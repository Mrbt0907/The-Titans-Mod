# The Titans Mod Version 0.6 - 1.12.2 
Hi

## Dependencies
- None

## Questions? Suggestions? Bugs?
You can report any of these in the Issues tab, and/or place your questions and suggestions in the Discussions tab.

## How To Get The Mod
Head to the releases tab on the right and grab the version of the mod you want (When any are available).

## How To Setup (Eclipse)
- Requires MrbtUtil-3.0.jar in libs folder (Create the folder in your project folder if it doesn't exist)
  - To get a copy of the jar, head to https://github.com/Mrbt0907/Minecraft-Adventures-Core and build that project for the jar file
- run "gradlew setupDecompWorkspace eclipse"

*If you encounter any other issues with the project, try reading https://github.com/quat1024/modern-forge-1.12-template?tab=readme-ov-file#common-problems*

## How To Build
- Requires MrbtUtil-3.0.jar in libs folder (Create the folder in your project folder if it doesn't exist)
  - To get a copy of the jar, head to https://github.com/Mrbt0907/Minecraft-Adventures-Core and build that project for the jar file
- run "gradlew build"
- Locate the resulting file in build/libs
- Extract the contents of the jar file preferably in a separate folder
- Locate the file mixin.refmap.json and open it
- Change all instances of "net/mrbt0907/util/mixin/injection" to "net/mrbt0907/thetitans/util/mixin/injection" and save
- Zip up all the contents again into the .zip format
- Rename the file extension .zip to .jar
  
## Supported Mods
There is currently no additional support for any mods at this time for this version

*If you want to chat with people or just want to say hi, feel free to join our discord: https://discord.gg/pN5CRR7*