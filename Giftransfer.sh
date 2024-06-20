#!/bin/bash

# Define the shell command to be run on each BFA_DM11_* file
SHELL_COMMAND="your_shell_command_here"

# Loop through all files matching BDL_*ACKYINFO_UDP*.xml
for bdl_file in BDL_*ACKYINFO_UDP*.xml; do
    if [[ -e "$bdl_file" ]]; then
        # Extract the common part of the filename to match BFA_DM11_* files
        common_part=$(echo "$bdl_file" | sed -e 's/^BDL_\(.*\)_ACKYINFO_UDP.*\.xml$/\1/')
        
        # Find corresponding BFA_DM11_* files
        for bfa_file in BFA_DM11_"$common_part"*.xml; do
            if [[ -e "$bfa_file" ]]; then
                # Append the content of the BDL file to the BFA file
                cat "$bdl_file" >> "$bfa_file"
                
                # Run the specified shell command on the updated BFA file
                $SHELL_COMMAND "$bfa_file"
            fi
        done
    fi
done
