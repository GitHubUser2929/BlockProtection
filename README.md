BlockProtection:
=
		A block protection plugin based off of OwnBlocks by cvenomz. 
		This plugin will protect blocks placed by players. 
		Only the player who placed the block, anyone on his friends list, or an admin can break the block. 

Features:
--
		* Block protection 
			Every block your players place will be protected in the database.
			
		* Admin bypass
			Your admins can bypass BlockProtection and break any player's blocks.
			
		* Friends System
			Allow your players to build with friends! They can add/remove them as needed.

Commands:
--
		* bp:
			Toggle BlockProtection on/off

		* bpadd:
			Add a player to your friends list 

		* bpadmin:
			Toggle BlockProtection bypass on/off

		* bpclear:
			Clears your entire friends list

		* bplist:
			List all players from your friends list

		* bpreload:
			Reloads settings from config.yml

		* bpremove:
			Remove a player from friends list

		* bptool:
			Gives the player 1 of the "utility tool"
 
Permissions:
--
		* bp.admin
			Allows players bypass BlockProtection, add blocks manually, and get the utility tool

		* bp.friend
			Allows access to all of the Friends List features 

		* bp.reload
			Allows admins to reload all settings from the configuration file

		* bp.user
			Allow players to use BlockProtection, and view the owner of blocks


Credits:
-
		BlockProtection was made from the ashes of an inactive plugin called OwnBlocks by cvenomz.
		I have attempted to fix up/recode as much as possible to keep up with the changes to Bukkit.
		All existing code is credited to cvenomz.

