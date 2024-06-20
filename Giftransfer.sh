#!/bin/bash

# Set the input directory
DM_IN_DIR="/path/to/your/input/directory"

# Find files matching the pattern BDL_*ACKYINFO_UDP*.xml
find "$DM_IN_DIR" -type f -name "BDL_*ACKYINFO_UDP*.xml" | while read -r file; do
    # Get the filename without the path
    filename=$(basename "$file")
    # Append "BFA_DM11_" to the filename
    new_filename="BFA_DM11_$filename"
    # Move the file with the new filename
    mv "$file" "$DM_IN_DIR/$new_filename"
    # Execute the renamed file using ksh
    ksh "$DM_IN_DIR/$new_filename"
done
