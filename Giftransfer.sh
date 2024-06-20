#!/bin/bash

# Directory where the files are located
DIR="/path/to/your/files"

# Loop through each BDL_*ACKYINFO_UDP*.xml file
for bdl_file in "${DIR}"/BDL_*ACKYINFO_UDP*.xml; do
  # Extract the unique identifier from the BDL file name
  unique_id=$(basename "$bdl_file" | sed 's/^BDL_//;s/_ACKYINFO_UDP.*\.xml$//')
  
  # Find the corresponding BFA_DM11_* file
  bfa_file=$(find "$DIR" -type f -name "BFA_DM11_${unique_id}*.xml")
  
  if [[ -f "$bfa_file" ]]; then
    # Append the content of the BDL file to the BFA file
    cat "$bdl_file" >> "$bfa_file"
    
    # Run the shell command using the BFA file
    # Replace `your_command_here` with the actual command you want to run
    your_command_here "$bfa_file"
  else
    echo "No corresponding BFA_DM11_* file found for $bdl_file"
  fi
done
