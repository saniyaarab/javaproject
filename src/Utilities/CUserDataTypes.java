// --------------------------------------------------------------------------------
// Name: CUserDataType
// Abstract: a collection of user defined types
// --------------------------------------------------------------------------------

// --------------------------------------------------------------------------------
// Packages
// --------------------------------------------------------------------------------
package Utilities;

//--------------------------------------------------------------------------------
//Imports
//--------------------------------------------------------------------------------
import java.math.BigDecimal;
import java.sql.Date;

public class CUserDataTypes
{
	//Declare and make instance
	//udtTeamType udtNewTeam = new CUserDataTypes( ).new udtTeamType( );
	
	//Team Structure
	public class udtTeamType
	{
		public int intTeamID = 0;
		public String strTeam = null;
		public String strMascot = null;
	}
	
	
	
	//Team Structure
	public class udtPlayerType
	{
		public int intPlayerID = 0;
		public String strFirstName = null;
		public String strMiddleName = null;
		public String strLastName = null;
		public String strStreetAddress = null;
		public String strCity = null;
		public int intStateID = 0;
		public String strZipCode = null;
		public String strHomePhoneNumber = null;
		public BigDecimal bdecSalary = null;
		public Date sdtmDateOfBirth = null;
	}


}
