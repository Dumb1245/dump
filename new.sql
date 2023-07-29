SELECT 
  OC.refudefmodule_name AS module_name, 
  OC.refudefvalue AS Output_directory,
  Counts.module_count
FROM 
(
  SELECT refudefmodule_name, refudefvalue 
  FROM refudefob_base_config 
  WHERE refudefvalue LIKE '%atlassian%'
) OC
JOIN 
(
  SELECT refudefmodule_name, COUNT(*) AS module_count 
  FROM refudefob_base_config
  GROUP BY refudefmodule_name
) Counts ON OC.refudefmodule_name = Counts.refudefmodule_name;
