// --------------------------------------------------------------------------------
// Name: CDatabaseUtilities
// Abstract: All procedures that directly interact with the database
// --------------------------------------------------------------------------------

// --------------------------------------------------------------------------------
// Packages
// --------------------------------------------------------------------------------
package Utilities;

// --------------------------------------------------------------------------------
// Imports
// --------------------------------------------------------------------------------
import java.sql.*;
import Utilities.CUserDataTypes.udtPlayerType;
import Utilities.CUserDataTypes.udtTeamType;

public class CDatabaseUtilities
{
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	// Properties
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	private static Connection m_conAdministrator 			= null;
	
	
	
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	// Methods
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	
	
	
	// --------------------------------------------------------------------------------
	// Name: OpenDatabaseConnection
	// Abstract : Open a connection to the database
	// --------------------------------------------------------------------------------
	public static boolean OpenDatabaseConnection( )
	{
		boolean blnResult = false;
		
		try
		{
			String strConnectionString = "";
			
			//Register special Driver
			Class.forName( "net.ucanaccess.jdbc.UcanaccessDriver" );
			
			//Server.getProperty("User.dir")
			strConnectionString += "jdbc:ucanaccess://" + System.getProperty(  "user.dir" )+ "\\Database\\TeamsAndPlayers4.accdb";
			
			//Special driver requires foreslashes (/)
			strConnectionString = strConnectionString.replace( '\\',  '/' );
			
			//open a connection to database
			m_conAdministrator = DriverManager.getConnection( strConnectionString );
			
			//Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return blnResult;
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: CloseDatabaseConnection
	// Abstract : Open a connection to the database
	// --------------------------------------------------------------------------------
	public static boolean CloseDatabaseConnection( )
	{
		boolean blnResult = false; 
		
		try
		{
			//is there a connection object?
			if( m_conAdministrator != null)
			{
				//yes, close the connection if not closed already
				if( m_conAdministrator.isClosed( ) == false )
				{
					m_conAdministrator.close( );
					
					//Prevent JVM from crashing
					m_conAdministrator = null;
				}				
			}
			
			//Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
		
		return blnResult;
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: LoadListBoxFromDatabase
	// Abstract : load a target list with the values from the specified table
	// --------------------------------------------------------------------------------
	public static boolean LoadListBoxFromDatabase(	String strTable, String strPrimaryKeyColumn,
													String strNameColumn, CListBox lstTarget )
	
	{
		boolean blnResult = false; 
		
		try
		{
			String strSelect= "";
			Statement sqlCommand = null;
			ResultSet rstTSource = null;
			int intID = 0;
			String strName = "";
			
			// Clear List
			lstTarget.Clear( );
			
			//Build the SQl String
			strSelect = "SELECT " + strPrimaryKeyColumn + ", " + strNameColumn + " FROM " + strTable + " ORDER BY " + strNameColumn;
			
			//Retrieve all the Records
			sqlCommand = m_conAdministrator.createStatement( );
			rstTSource = sqlCommand.executeQuery(  strSelect );
			
			//Loop through all the records
			while( rstTSource.next( ) == true )
			{
				//Get Id and Name from current roq
				intID = rstTSource.getInt( 1 );
				strName = rstTSource.getString( 2 );
				
				//Add the item to the list (0 = id, strTeam = team to display, false = don't select)
				lstTarget.AddItemToList( intID, strName, false );
			}
			
			//Select first item in list by default
			if( lstTarget.Length( ) > 0 ) lstTarget.SetSelectedIndex( 0 );
			
			//Clean up
			rstTSource.close( );
			sqlCommand.close( );
			
			//Success
			blnResult = true;
		}
		catch( Exception expError )
		{
			//Display Error Message
			CUtilities.WriteLog( expError );
		}	
		
		return blnResult;
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: LoadComboBoxFromDatabase
	// Abstract : load a target list with the values from the specified table
	// --------------------------------------------------------------------------------
	public static boolean LoadComboBoxFromDatabase(	String strTable, String strPrimaryKeyColumn,
													String strNameColumn, CComboBox cmbTarget )
	
	{
		boolean blnResult = false; 
		
		try
		{
			String strSelect= "";
			Statement sqlCommand = null;
			ResultSet rstTSource = null;
			int intID = 0;
			String strName = "";
			
			// Clear List
			cmbTarget.Clear( );
			
			//Build the SQl String
			strSelect = "SELECT " + strPrimaryKeyColumn + ", " + strNameColumn + " FROM " + strTable + " ORDER BY " + strNameColumn;
			
			//Retrieve all the Records
			sqlCommand = m_conAdministrator.createStatement( );
			rstTSource = sqlCommand.executeQuery(  strSelect );
			
			//Loop through all the records
			while( rstTSource.next( ) == true )
			{
				//Get Id and Name from current roq
				intID = rstTSource.getInt( 1 );
				strName = rstTSource.getString( 2 );
				
				//Add the item to the list (0 = id, strTeam = team to display, false = don't select)
				cmbTarget.AddItemToList( intID, strName, false );
			}
			
			//Select first item in list by default
			if( cmbTarget.Length( ) > 0 ) cmbTarget.SetSelectedIndex( 0 );
			
			//Clean up
			rstTSource.close( );
			sqlCommand.close( );
			
			//Success
			blnResult = true;
		}
		catch( Exception expError )
		{
			//Display Error Message
			CUtilities.WriteLog( expError );
		}	
		
		return blnResult;
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: GetNextHighestRecordID
	// Abstract : Get the next highest ID from he table in the database
	// --------------------------------------------------------------------------------	
	public static int GetNextHighestRecordID( String strPrimaryKey, String strTable )
	{
		int intNextHighestRecordID = 0;
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTable = null;
			
			//Build Command
			strSQL = "Select MAX( " + strPrimaryKey + " ) + 1 AS intHighestRecordID"
				   + " FROM " + strTable;
			
			//Execute Command
			sqlCommand = m_conAdministrator.createStatement( );
					rstTable = sqlCommand.executeQuery( strSQL );
			
			//Read result (nest Highest ID )
			if( rstTable.next( ) )
			{
				//Next highest ID
				intNextHighestRecordID = rstTable.getInt( "intHighestRecordID" );
			}
			else
			{
				//Table empty. Start at 1. NEVER start at 0.
				intNextHighestRecordID = 1;
			}
			
			//Clean Up
			rstTable.close( );
			sqlCommand.close( );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		//Return 
		return intNextHighestRecordID;
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: DeleteRecordsFromTable
	// Abstract : Delete all records from table to match the ID
	// --------------------------------------------------------------------------------	
	private static boolean DeleteRecordsFromTable( int intRecordID, String strPrimaryKeyColumn, String strTable )
	{
		boolean blnResult = false;
		
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			
			//Build the SQL String
			strSQL = "DELETE FROM " + strTable + " WHERE " + strPrimaryKeyColumn + " = " + intRecordID;
			
			//Do it
			sqlCommand = m_conAdministrator.createStatement( );
			sqlCommand.execute(  strSQL );
			
			//Clean up
			blnResult = true;
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return blnResult;
	}
	
	
	//--------------------------------------------------------------------------------
	//	Teams Procedures
	//  Add, Get Information, Edit and Delete procedures for Team
	//--------------------------------------------------------------------------------
	
	
	
	// --------------------------------------------------------------------------------
	// Name: AddTeamToDatabase
	// Abstract : Add Team to the database
	// --------------------------------------------------------------------------------	
	public static boolean AddTeamToDatabase( udtTeamType udtTeam)
	{
		boolean blnResult = false;
		
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTTeams = null;
			
			//Get the next highest team ID and same in suitcase so its return to Add form
			udtTeam.intTeamID = GetNextHighestRecordID( "intTeamID", "TTeams");
			
			//Race Condition. MS Access doesn't Support locking or stores procedures
			
			//Build the select string ( select no records )
			strSQL = "Select * FROM TTeams WHERE intTeamID = -1";
			
			//Retrieve the all the records
			sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			
			rstTTeams = sqlCommand.executeQuery( strSQL );
			
			// New row using table structure returned from empty select
			rstTTeams.moveToInsertRow( );
			rstTTeams.updateInt( "intTeamID", udtTeam.intTeamID );
			rstTTeams.updateString( "strTeam", udtTeam.strTeam );
			rstTTeams.updateString( "strMascot", udtTeam.strMascot );
			rstTTeams.updateInt( "strTeamStatusID", CConstants.intTEAM_STATUS_ACTIVE );
			
			//Make the changes stick
			rstTTeams.insertRow( );
			
			//Clean up
			rstTTeams.close( );
			sqlCommand.close( );
			
			//Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		//Return 
		return blnResult;
		
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: GetTeamInformationFromDatabase
	// Abstract : Get Information for specified team from database
	// --------------------------------------------------------------------------------	
	public static boolean GetTeamInformationFromDatabase( udtTeamType udtTeam )
	{
		boolean blnResult = false;
		
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTTeams = null;
			
			//Build the select string
			strSQL = "SELECT * FROM TTeams WHERE intTeamID = " + udtTeam.intTeamID;
			
			//Retrieve the record
			sqlCommand = m_conAdministrator.createStatement( );
			rstTTeams = sqlCommand.executeQuery( strSQL );
			
			//Get the teams Information (Should be 1 and 1 row)
			rstTTeams.next( );
			udtTeam.strTeam = rstTTeams.getString( "strTeam" );
			udtTeam.strMascot = rstTTeams.getString( "strMascot" );
			
			//Clean up
			rstTTeams.close( );
			sqlCommand.close( );
			
			//Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return blnResult;		
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: EditTeamInDatabase
	// Abstract : Update Information for specified team from database
	// --------------------------------------------------------------------------------	
	public static boolean EditTeamInDatabase( udtTeamType udtTeam )
	{
		boolean blnResult = false;
		
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTTeams = null;
			
			//Build the select string
			strSQL = "SELECT * FROM TTeams WHERE intTeamID = " + udtTeam.intTeamID;
			
			//Retrieve the record
			sqlCommand = m_conAdministrator.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			rstTTeams = sqlCommand.executeQuery( strSQL );
			
			//Edit the teams Information (Should be 1 and 1 row)
			rstTTeams.next( );
			rstTTeams.updateString( "strTeam", udtTeam.strTeam );
			rstTTeams.updateString( "strMascot", udtTeam.strMascot );
			
			//Make changes stick
			rstTTeams.updateRow( );		
			
			//Clean up
			rstTTeams.close( );
			sqlCommand.close( );
			
			//Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return blnResult;		
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: DeleteTeamFromDatabase
	// Abstract : Mark the specified team as inactive
	// --------------------------------------------------------------------------------	
	public static boolean DeleteTeamFromDatabase( int intTeamID )
	{
		boolean blnResult = false;
		
		try
		{
			blnResult = SetTeamStatusInDatabase( intTeamID, CConstants.intTEAM_STATUS_INACTIVE );
		}
		catch ( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return blnResult;
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: UndeleteTeamFromDatabase
	// Abstract : Mark the specified team as active
	// --------------------------------------------------------------------------------	
	public static boolean UndeleteTeamFromDatabase( int intTeamID )
	{
		boolean blnResult = false;
		
		try
		{
			blnResult = SetTeamStatusInDatabase( intTeamID, CConstants.intTEAM_STATUS_ACTIVE );
		}
		catch ( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return blnResult;
	}

	
	
	// --------------------------------------------------------------------------------
	// Name: SetTeamStatusInDatabase
	// Abstract : Mark the Specified Team as Inactive
	// --------------------------------------------------------------------------------	
	private static boolean SetTeamStatusInDatabase( int intTeamID, int intTeamStatusID )
	{
		boolean blnResult = false;
		
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTTeams = null;
			
			//Build the select string
			strSQL = "SELECT * FROM TTeams WHERE intTeamID = " + intTeamID;
			
			//Retrieve the record
			sqlCommand = m_conAdministrator.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			rstTTeams = sqlCommand.executeQuery( strSQL );
			
			//Edit the Players Information (Should be 1 and 1 row)
			rstTTeams.next( );
			rstTTeams.updateInt( "intTeamStatusID", intTeamStatusID );
			
			//Make changes stick
			rstTTeams.updateRow( );		
			
			//Clean up
			rstTTeams.close( );
			sqlCommand.close( );
			
			//Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return blnResult;		
	}
	
	
	
	//--------------------------------------------------------------------------------
	//Team Players
	//Load Team Players
	//--------------------------------------------------------------------------------
	
	
	
	// --------------------------------------------------------------------------------
	// Name: LoadListWithPlayersFromDatabase
	// Abstract : load a target list with the values from the specified table
	// --------------------------------------------------------------------------------
	public static boolean LoadListWithPlayersFromDatabase(	int intTeamID, CListBox lstTarget, boolean blnPlayersOnTeam )
	
	{
		boolean blnResult = false; 
		
		try
		{
			String strSelect= "";
			Statement sqlCommand = null;
			ResultSet rstTTeamPlayers = null;
			String strNot = "NOT";
			int intID = 0;
			String strName = "";
			
			// Clear List
			lstTarget.Clear( );
			
			//Selected Players? Yes
			if( blnPlayersOnTeam == true ) strNot = "";
			
 			//Build the SQl String
			//Load All Players
			strSelect = "SELECT intPlayerID, strLastName + ', ' + strFirstName"
					  +	" FROM  VActivePlayers" 
					  //..... That are/aren't
					  + " WHERE intPlayerID " + strNot + " IN "
					  //..on the team
					  + "	(SELECT intPlayerID FROM TTeamPlayers WHERE intTeamID = " + intTeamID + " )"
					  + " ORDER BY strLastName, strFirstName";
			
			//Retrieve all the Records
			sqlCommand = m_conAdministrator.createStatement( );
			rstTTeamPlayers = sqlCommand.executeQuery(  strSelect );
			
			//Loop through all the records
			while( rstTTeamPlayers.next( ) == true )
			{
				//Get Id and Name from current row
				intID = rstTTeamPlayers.getInt( 1 );
				strName = rstTTeamPlayers.getString( 2 );
				
				//Add the item to the list (0 = id, strTeam = team to display, false = don't select)
				lstTarget.AddItemToList( intID, strName, false );
			}
			
			//Select first item in list by default
			if( lstTarget.Length( ) > 0 ) lstTarget.SetSelectedIndex( 0 );
			
			//Clean up
			rstTTeamPlayers.close( );
			sqlCommand.close( );
			
			//Success
			blnResult = true;
		}
		catch( Exception expError )
		{
			//Display Error Message
			CUtilities.WriteLog( expError );
		}	
		
		return blnResult;
	}
	
	
	
	//-----------------------------------------------------------------------------------
	// Name: AddPlayerToTeamInDatabase
	// Abstract : Add Player to the database
	// --------------------------------------------------------------------------------	
	public static boolean AddPlayerToTeamInDatabase( int intTeamID, int intPlayerID ) 
	{
		boolean blnResult = false;
		
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTTeamPlayers = null;
			
			//Build the select string ( select no records )
			strSQL = "Select * FROM TTeamPlayers WHERE intTeamID = -1";
			
			//Retrieve the all the records
			sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			
			rstTTeamPlayers = sqlCommand.executeQuery( strSQL );
			
			// New row using table structure returned from empty select
			rstTTeamPlayers.moveToInsertRow( );
			rstTTeamPlayers.updateInt( 			"intTeamID", intTeamID );
			rstTTeamPlayers.updateInt( 			"intPlayerID", intPlayerID );
			
			//Make the changes stick
			rstTTeamPlayers.insertRow( );
			
			//Clean up
			rstTTeamPlayers.close( );
			sqlCommand.close( );
			
			//Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		//Return 
		return blnResult;
		
	}

	
	// --------------------------------------------------------------------------------
	// Name: RemovePlayerFromTeamInDatabase
	// Abstract : Update Information for specified Player from database
	// --------------------------------------------------------------------------------	
	public static boolean RemovePlayerFromTeamInDatabase( int intTeamID, int intPlayerID )
		{
			boolean blnResult = false;
			
			try
			{
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTTeamPlayers = null;
				
				//Build the select string
				strSQL 	= 	"SELECT * FROM TTeamPlayers WHERE intTeamID = " + intTeamID + 
							" AND intPlayerID = " + intPlayerID ;
				
				//Retrieve the record
				sqlCommand = m_conAdministrator.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
				rstTTeamPlayers = sqlCommand.executeQuery( strSQL );
				
				//Edit the Players Information (Should be 1 and 1 row)
				rstTTeamPlayers.next( );
				rstTTeamPlayers.deleteRow( );		
				
				//Clean up
				rstTTeamPlayers.close( );
				sqlCommand.close( );
				
				//Success
				blnResult = true;
			}
			catch( Exception excError )
			{
				//Display Error Message
				CUtilities.WriteLog( excError );
			}
			
			return blnResult;		
		}
	
	
	
	//--------------------------------------------------------------------------------
	//Players
	//Add, Get Information, Edit and delete procedures for Players
	//--------------------------------------------------------------------------------
	
	
	
	//-----------------------------------------------------------------------------------
	// Name: AddPlayerToDatabase
	// Abstract : Add Player to the database
	// --------------------------------------------------------------------------------	
	public static boolean AddPlayerToDatabase( udtPlayerType udtPlayer)
	{
		boolean blnResult = false;
		
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTPlayers = null;
			
			//Get the next highest Player ID and same in suitcase so its return to Add form
			udtPlayer.intPlayerID = GetNextHighestRecordID( "intPlayerID", "TPlayers");
			
			//Race Condition. MS Access doesn't Support locking or stores procedures
			
			//Build the select string ( select no records )
			strSQL = "Select * FROM TPlayers WHERE intPlayerID = -1";
			
			//Retrieve the all the records
			sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			
			rstTPlayers = sqlCommand.executeQuery( strSQL );
			
			// New row using table structure returned from empty select
			rstTPlayers.moveToInsertRow( );
			rstTPlayers.updateInt( 			"intPlayerID", udtPlayer.intPlayerID );
			rstTPlayers.updateString( 		"strFirstName", udtPlayer.strFirstName );
			rstTPlayers.updateString( 		"strMiddleName", udtPlayer.strMiddleName );
			rstTPlayers.updateString( 		"strLastName", udtPlayer.strLastName );
			rstTPlayers.updateString( 		"strStreetAddress", udtPlayer.strStreetAddress );
			rstTPlayers.updateString( 		"strCity", udtPlayer.strCity );
			rstTPlayers.updateInt( 			"intStateID", udtPlayer.intStateID );
			rstTPlayers.updateString( 		"strZipCode", udtPlayer.strZipCode );
			rstTPlayers.updateString( 		"strHomePhoneNumber", udtPlayer.strHomePhoneNumber );
			rstTPlayers.updateBigDecimal( 	"curSalary", udtPlayer.bdecSalary );
			rstTPlayers.updateDate( 		"dbmDateOfBirth", udtPlayer.sdtmDateOfBirth );
			rstTPlayers.updateInt( 			"strPlayerStatusID", CConstants.intPLAYER_STATUS_ACTIVE );
			//Make the changes stick
			rstTPlayers.insertRow( );
			
			//Clean up
			rstTPlayers.close( );
			sqlCommand.close( );
			
			//Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		//Return 
		return blnResult;
		
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: GetPlayerInformationFromDatabase
	// Abstract : Get Information for specified Player from database
	// --------------------------------------------------------------------------------	
	public static boolean GetPlayerInformationFromDatabase( udtPlayerType udtPlayer )
	{
		boolean blnResult = false;
		
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTPlayers = null;
			
			//Build the select string
			strSQL = "SELECT * FROM TPlayers WHERE intPlayerID = " + udtPlayer.intPlayerID;
			
			//Retrieve the record
			sqlCommand = m_conAdministrator.createStatement( );
			rstTPlayers = sqlCommand.executeQuery( strSQL );
			
			//Get the Players Information (Should be 1 and 1 row)
			rstTPlayers.next( );
			udtPlayer.strFirstName = rstTPlayers.getString( "strFirstName" );
			udtPlayer.strMiddleName = rstTPlayers.getString( "strMiddleName" );
			udtPlayer.strLastName = rstTPlayers.getString( "strLastName" );
			udtPlayer.strStreetAddress = rstTPlayers.getString( "strStreetAddress" );
			udtPlayer.strCity = rstTPlayers.getString( "strCity" );
			udtPlayer.intStateID = rstTPlayers.getInt( "intStateID" );
			udtPlayer.strZipCode = rstTPlayers.getString( "strZipCode" );
			udtPlayer.strHomePhoneNumber = rstTPlayers.getString( "strHomePhoneNumber" );
			udtPlayer.bdecSalary = rstTPlayers.getBigDecimal( "curSalary" );
			
			//Clean up
			rstTPlayers.close( );
			sqlCommand.close( );
			
			//Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return blnResult;		
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: EditPlayerInDatabase
	// Abstract : Update Information for specified Player from database
	// --------------------------------------------------------------------------------	
	public static boolean EditPlayerInDatabase( udtPlayerType udtPlayer )
	{
		boolean blnResult = false;
		
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTPlayers = null;
			
			//Build the select string
			strSQL = "SELECT * FROM TPlayers WHERE intPlayerID = " + udtPlayer.intPlayerID;
			
			//Retrieve the record
			sqlCommand = m_conAdministrator.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			rstTPlayers = sqlCommand.executeQuery( strSQL );
			
			//Edit the Players Information (Should be 1 and 1 row)
			rstTPlayers.next( );
			rstTPlayers.updateString( "strFirstName", udtPlayer.strFirstName );
			rstTPlayers.updateString( "strMiddleName", udtPlayer.strMiddleName );
			rstTPlayers.updateString( "strLastName", udtPlayer.strLastName );
			rstTPlayers.updateString( "strStreetAddress", udtPlayer.strStreetAddress );
			rstTPlayers.updateString( "strCity", udtPlayer.strCity );
			rstTPlayers.updateInt( "intStateID", udtPlayer.intStateID );
			rstTPlayers.updateString( "strZipCode", udtPlayer.strZipCode );
			rstTPlayers.updateString( "strHomePhoneNumber", udtPlayer.strHomePhoneNumber );
			rstTPlayers.updateBigDecimal( "curSalary", udtPlayer.bdecSalary );
			
			//Make changes stick
			rstTPlayers.updateRow( );		
			
			//Clean up
			rstTPlayers.close( );
			sqlCommand.close( );
			
			//Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return blnResult;		
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: DeletePlayerFromDatabase
	// Abstract : Mark the specified Player as inactive
	// --------------------------------------------------------------------------------	
	public static boolean DeletePlayerFromDatabase( int intPlayerID )
	{
		boolean blnResult = false;
		
		try
		{
			blnResult = SetPlayerStatusInDatabase( intPlayerID, CConstants.intPLAYER_STATUS_INACTIVE );
		}
		catch ( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return blnResult;
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: UndeletePlayerFromDatabase
	// Abstract : Mark the specified Player as active
	// --------------------------------------------------------------------------------	
	public static boolean UndeletePlayerFromDatabase( int intPlayerID )
	{
		boolean blnResult = false;
		
		try
		{
			blnResult = SetPlayerStatusInDatabase( intPlayerID, CConstants.intPLAYER_STATUS_ACTIVE );
		}
		catch ( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return blnResult;
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: SetPlayerStatusInDatabase
	// Abstract : Mark the Specified Player as Inactive
	// --------------------------------------------------------------------------------	
	private static boolean SetPlayerStatusInDatabase( int intPlayerID, int intPlayerStatusID )
	{
		boolean blnResult = false;
		
		try
		{
			String strSQL = "";
			Statement sqlCommand = null;
			ResultSet rstTPlayers = null;
			
			//Build the select string
			strSQL = "SELECT * FROM TPlayers WHERE intPlayerID = " + intPlayerID;
			
			//Retrieve the record
			sqlCommand = m_conAdministrator.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			rstTPlayers = sqlCommand.executeQuery( strSQL );
			
			//Edit the Players Information (Should be 1 and 1 row)
			rstTPlayers.next( );
			rstTPlayers.updateInt( "intPlayerStatusID", intPlayerStatusID );
			
			//Make changes stick
			rstTPlayers.updateRow( );		
			
			//Clean up
			rstTPlayers.close( );
			sqlCommand.close( );
			
			//Success
			blnResult = true;
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return blnResult;		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
