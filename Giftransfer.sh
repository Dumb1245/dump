#!/bin/bash

# Define the directory containing the files
DM_IN_DIR="/path/to/your/directory"

# Iterate over files matching the pattern in the specified directory
for file in "$DM_IN_DIR"/BDL_*ACKYINFO_UDP*.xml; do
  # Check if the file exists to avoid errors if no files match
  if [[ -e "$file" ]]; then
    # Extract the base filename from the full path
    base_filename=$(basename "$file")
    
    # Construct the new filename by appending the prefix
    new_filename="BFA_DM11_${base_filename}"

    # Define the full path to the new file
    new_filepath="$DM_IN_DIR/$new_filename"

    # Execute your shell command using the new filename
    # For example, let's just echo the new filename
    # Replace 'echo' with the actual command you want to run
    echo "Executing command on $new_filepath"
    
    # Uncomment and modify the line below to run the actual command
    # your_command_here "$new_filepath"
  fi
done
