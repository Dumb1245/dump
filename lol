import org.json.JSONArray;
import org.json.JSONObject;

public class BFAJobProcessor {

    public static JSONObject processACKYINFOJobs(String responseContent) throws Exception {
        JSONObject jobIdsObj = new JSONObject();
        JSONArray responseArr = new JSONArray(responseContent);

        for (int i = 0; i < responseArr.length(); i++) {
            JSONObject obj = responseArr.getJSONObject(i);
            if (obj.get("name").equals("ACKYINFO - Create BDL")) {
                jobIdsObj.put("createBDLJobId", obj.getInt("id"));
            } else if (obj.get("name").equals("ACKYINFO UDP - Load BDL")) {
                jobIdsObj.put("loadBDLJobId", obj.getInt("id"));
            }
        }
        return jobIdsObj;
    }

    public static JSONObject processDEBTSALEJobs(String responseContent) throws Exception {
        JSONObject jobIdsObj = new JSONObject();
        JSONArray responseArr = new JSONArray(responseContent);

        for (int i = 0; i < responseArr.length(); i++) {
            JSONObject obj = responseArr.getJSONObject(i);
            if (obj.get("name").equals("DEBTSALE - Create BDL")) {
                jobIdsObj.put("createBDLJobId", obj.getInt("id"));
            } else if (obj.get("name").equals("DEBTSALE UDP - Load BDL")) {
                jobIdsObj.put("loadBDLJobId", obj.getInt("id"));
            }
        }
        return jobIdsObj;
    }

    public static JSONObject callBPOAPI(String createJob, String loadJob) throws Exception {
        DM11_BFA_Common.log.info("Fetching all the jobs");
        JSONObject response = DM11_BFA_Common.make_call_bpo(resource: "/jobs", token: true, vt: true, config: "GET");

        int createBDLJobId = response.getInt(key: "createBDLJobId");
        int loadBDLJobId = response.getInt(key: "loadBDLJobId");

        DM11_BFA_Common.log.info("Starting the BDL job with Id : " + createBDLJobId);
        JSONObject resStartJob = DM11_BFA_Common.make_call_bpo(resource: "/startjob/" + createBDLJobId, token: true, vt: true, config: "POST");
        DM11_BFA_Common.log.info("Create BDL job completed successfully with Id : " + createBDLJobId);

        int jobStatusId = resStartJob.getInt(key: "id");

        // Add date logic as per your original code
        Date curr_date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern: "yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(dateFormat.parse(dateFormat.format(curr_date)));
        c.add(Calendar.DATE, amount: 1);
        String next_date = dateFormat.format(c.getTime());

        DM11_BFA_Common.log.info("Calling Create BDL job log API with Id : " + createBDLJobId);
        JSONObject resJob = DM11_BFA_Common.make_call_bpo(resource: "/jobs/" + createBDLJobId + "/runs", token: true, vt: true, config: "POST",
                "{" +
                "  \"fromDate\": \"" + dateFormat.format(curr_date) + "\"," +
                "  \"toDate\": \"" + next_date + "\"" +
                "}");

        return resJob;
    }

    public static void main(String[] args) {
        try {
            // Example: Process ACKYINFO jobs
            String ackyInfoResponse = DM11_BFA_Common.make_call_bpo(resource: "/jobs", token: true, vt: true, config: "GET").toString();
            JSONObject ackyInfoJobIds = processACKYINFOJobs(ackyInfoResponse);
            System.out.println("ACKYINFO Job IDs: " + ackyInfoJobIds.toString());

            // Example: Process DEBTSALE jobs
            String debtsaleResponse = DM11_BFA_Common.make_call_bpo(resource: "/jobs", token: true, vt: true, config: "GET").toString();
            JSONObject debtsaleJobIds = processDEBTSALEJobs(debtsaleResponse);
            System.out.println("DEBTSALE Job IDs: " + debtsaleJobIds.toString());

            // Run ACKYINFO Create BDL job
            if (ackyInfoJobIds.has("createBDLJobId")) {
                callBPOAPI(createJob: "ACKYINFO - Create BDL", loadJob: null);
            }

            // Run DEBTSALE Create BDL job
            if (debtsaleJobIds.has("createBDLJobId")) {
                callBPOAPI(createJob: "DEBTSALE - Create BDL", loadJob: null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
