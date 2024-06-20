#!/bin/bash

# Define the input directory
DM_IN_DIR="/path/to/directory" # Update this with your actual directory

# Find all matching .xml and .xml.done files and process them
for file in "$DM_IN_DIR"/BDL_*ACKYINFO_UDP*.xml "$DM_IN_DIR"/BDL_*ACKYINFO_UDP*.xml.done
do
  # Check if the file exists (in case no files match the pattern)
  if [ -e "$file" ]; then
    # Extract the base filename without the directory path
    base_filename=$(basename "$file")
    
    # Create the new filename by appending BFA_DM11_ to the base filename
    new_filename="BFA_DM11_$base_filename"
    
    # Move (rename) the file
    mv "$file" "$DM_IN_DIR/$new_filename"
    
    # Run the shell command using the updated filename
    # Replace 'your_command_here' with the actual command you want to run
    your_command_here "$DM_IN_DIR/$new_filename"
  fi
done
