Changelog
=
v2.5.3 (01/12/2012)
--
	* Lots of code revision
	* Block breaks are now cancelled when holding the "utility-tool"
	* /bptool command now checks to see if your inventory is full
	* Moved all configuration items to a new ConfigHandler class
	* Other things I can't remember....

v2.5.2 (01/03/2012)
--
	* Minor code revisions

v2.5.1 (12/30/2011)
--
	* Added support for almost all permissions systems via Vault. (Vault is REQUIRED)
	* bp.friend permission now defaults to true
	* bp.admin permission now defaults to op
	* Moved all commands to their own class files
	* Did some minor code cleanup
	* Database system overhaul. Should be more space-efficient. Thanks phoenix7782!

v2.5 (12/15/2011)
--
       * Grouped existing permission nodes
           - View block information & command /bp now run off of node: bp.user
           - All "Friends System" commands now run off of node: bp.friend
           - /bpadmin, /bptool, & adding block manually now run off of node: bp.admin
           
       * Changed blockprotection.reload node to bp.reload and changed default to Op
       
       * Renamed "Buddy System" to "Friends System" (Friends List doesn't sound so lame)
       
       * Moved all "Friends System" commands to their own class
           - Fixed /bpadd command. It no longer throws errors when trying to add friends when
             your friends list doesn't really exist
           - Fixed /bpremove command throwing NPE error when checking if your friends list
             is empty before deleting.
             
       * Fixed permission for /bptool. (bp.admin) No longer uses blockprotection.buddy.
       
       * Added a setting for advanced logging. BlockProtection will log when an admin enables
         bypass mode, or when they use the /bptool command. It will also log when a player tries
         to use an admin command without the proper permissions. (/bptool, /bpadmin, /bpreload)
         
       * Fixed errors thrown by the reload command when sent from console.
       
       * Fixed error where the plugin could not properly load the utilitytool setting
       
       * Blocks are now checked against the exclude list when you try to add them manually.
       
       * Database now saves according to the "save-interval" setting in the config

v2.4 (12/13/2011)
--
       * Fixed errors thrown by the remove command
       
       * Added a check to delete a players buddy list when everyone is removed

v2.3 (12/13/2011)
--
       * Added a command to completely erase a players buddy list (/bpclear)

v2.2 (12/13/2011)
--
       * Added a command to give admins one (1) of the "utiltytool" (/bptool)

v2.1 (12/13/2011)
--
       * Added a command to list everyone in your buddy list (/bplist)

v2.0 (12/13/2011)
--
       * Designated plugin BlockProtection (orignial, eh?)
       
       * Edited code to reflect new designation
       
       * Added a command to reload settings from the configuration file
           - Added permission node (blockprotection.reload)
           
       * Added a command to toggle admin bypass mode (/bpadmin)
       
       * Added configuration setting to enable admin bypass by default
       
       * Implemented a "Buddy System"
           - Add permission node (blockprotection.buddy)
           - Added a command to add players to their buddy list (/bpadd)
           - Added a command to remove players from their buddy list (/bpadd)
       
v1.1 (12/12/2011)
--
       * Minor code revisions
       
       * Removed excess code
       
       * Combined info-id and add-id into "utilitytool"
   
v1.0 (12/12/2011)
--
       * Updated OwnBlocks to the newest recommended build.
       
       * Updated to the new configuration system.
       
       * Removed unwanted code.

