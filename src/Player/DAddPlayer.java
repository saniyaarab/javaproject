// --------------------------------------------------------------------------------
// Name: Saniya Arab
// Class: IT-161 Java #1
// Abstract: This is a class FMain for Homework 16 - DB - Players
// --------------------------------------------------------------------------------

//--------------------------------------------------------------------------------
// Package
// --------------------------------------------------------------------------------
package Player;

// --------------------------------------------------------------------------------
// Import
// --------------------------------------------------------------------------------
import java.awt.*; 
import java.awt.event.*;
import java.math.BigDecimal;
import javax.swing.*;
import Utilities.*;
import Utilities.CMessageBox.enuIconType;
import Utilities.CUserDataTypes.udtPlayerType;

@SuppressWarnings("serial")
public class DAddPlayer extends JDialog implements ActionListener, WindowListener
{
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	// Controls
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	private JLabel m_lblFirstName		 		= null;
	private JLabel m_lblMiddleName 	 			= null;
	private JLabel m_lblLastName		 		= null;
	private JLabel m_lblStreetAddress 	 		= null;
	private JLabel m_lblCity		 			= null;
	private JLabel m_lblState 	 				= null;
	private JLabel m_lblZipCode		 			= null;
	private JLabel m_lblRequired		 		= null;
	private JLabel m_lblHomePhoneNumber	 		= null;
	private JLabel m_lblSalary			 		= null;
	private JLabel m_lblDateOfBirth				= null;
	
	private CTextBox m_txtFirstName				= null;
	private CTextBox m_txtMiddleName			= null;
	private CTextBox m_txtLastName				= null;
	private CTextBox m_txtStreetAddress			= null;
	private CTextBox m_txtCity					= null;
	private CTextBox m_txtState					= null;
	private CTextBox m_txtZipCode				= null;
	private CTextBox m_txtHomePhoneNumber		= null;
	private CTextBox m_txtSalary				= null;
	private CTextBox m_txtDateOfBirth			= null;
	
	private CComboBox m_cmbState				= null;
	private JButton m_btnOK_Click				= null;
	private JButton m_btnCancel_Click			= null;

	
	
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	// Properties - never make public
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	private int m_intNewPlayerID = 0;
	private boolean m_blnResult = false;
	
	// --------------------------------------------------------------------------------
	// Name: DAddPlayer
	// Abstract: Constructor
	// --------------------------------------------------------------------------------
	public DAddPlayer( JDialog dlgParent )
	{
		super( dlgParent, true ); //true = modal
		
		try
		{	
			//Initialize this frame
			Initialize( );
			
			//Add controls
			AddControls( );
		}
		catch(Exception excError )
		{
			//Display Error message
			CUtilities.WriteLog( excError );
		}
	}
	

	
	// --------------------------------------------------------------------------------
	// Name: Initialize
	// Abstract: Center, set the title, etc
	// --------------------------------------------------------------------------------
	private void Initialize()
	{
		try
		{
			int intWidth = 370;
			int intHeight = 500;
			
			//Title
			setTitle( " Add Player" );
			
			//Size
			setSize( intWidth, intHeight);
			
			//Center Screen
			CUtilities.CenterOwner( this );
			
			//No resize
			setResizable( false );
			
			//Listen for window events
			this.addWindowListener( this );
			
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
	}	

	
	
	// --------------------------------------------------------------------------------
	// Name: paint
	// Abstract: Override the paint event to draw grid marks
	// --------------------------------------------------------------------------------
	public void paint( Graphics g )
	{
		super.paint(  g );
		try
		{
			//CUtilities.DrawGridMarks( this, g );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	//----------------------------------------------------------------------------------
	// Name: AddControls
	// Abstract : Add all the controls to the Frame
	// --------------------------------------------------------------------------------
	private void AddControls( )
	{
		try
		{
			//Clear layout manager so we can manually size and position controls
			this.setLayout( null );
			
			//First Name Label
			m_lblFirstName				= CUtilities.AddLabel( this, "First Name:", 20, 10 );
			//First Name Textbox
			m_txtFirstName				= CUtilities.AddTextBox( this, 20, 140, 25, 190, 50 );
			
			//Middle Name Label
			m_lblMiddleName				= CUtilities.AddLabel( this, "Middle Name:", 60, 10 );
			//Middle Name Textbox
			m_txtMiddleName				= CUtilities.AddTextBox( this, 60, 140, 25, 190, 50 );
			
			//LastName Label
			m_lblLastName				= CUtilities.AddLabel( this, "Last Name:", 100, 10 );
			//Last Name Textbox
			m_txtLastName				= CUtilities.AddTextBox( this, 100, 140, 25, 190, 50 );
			
			//StreetAddress Name Label
			m_lblStreetAddress			= CUtilities.AddLabel( this, "Street Address:", 140, 10 );
			//First Name Textbox
			m_txtStreetAddress			= CUtilities.AddTextBox( this, 140, 140, 25, 190, 50 );
			
			//City Name Label
			m_lblCity					= CUtilities.AddLabel( this, "City:", 180, 10 );
			//City Name Textbox
			m_txtCity					= CUtilities.AddTextBox( this, 180, 140, 25, 190, 50 );
			
			//State Label
			m_lblState					= CUtilities.AddLabel( this, "State:", 220, 10 );
			//State Combo Box
			m_cmbState					= CUtilities.AddComboBox( this, 220, 140, 25, 190 );
			
			//ZipCode Label
			m_lblZipCode				= CUtilities.AddLabel( this, "Zip Code:", 260, 10 );
			//ZipCode Textbox
			m_txtZipCode				= CUtilities.AddTextBox( this, 260, 140, 25, 190, 50 );
			
			//HomePhoneNumber Label
			m_lblHomePhoneNumber		= CUtilities.AddLabel( this, "Home Phone Number:", 300, 10 );
				CUtilities.AddColoredSizedLabel( this, 310, 10, "###-#### or ###-###-####", "999999", 0.8f );
			//HomePhoneNumber Textbox
			m_txtHomePhoneNumber		= CUtilities.AddTextBox( this, 300, 140, 25, 190, 50 );
			
			//Salary Label
			m_lblSalary					= CUtilities.AddLabel( this, "Salary:", 340, 10 );
			//Salary Textbox
			m_txtSalary					= CUtilities.AddTextBox( this, 340, 140, 25, 190, 50 );
			m_txtSalary.setHorizontalAlignment( JTextField.RIGHT ); //Right align
			
			//Date Of Birth Label
			m_lblDateOfBirth			= CUtilities.AddLabel( this, "Date Of Birth:", 380, 10 );
				CUtilities.AddColoredSizedLabel( this, 395, 15, "yyyy/mm/dd", "999999", 0.8f );
			//Date Of Birth Textbox
			m_txtDateOfBirth			= CUtilities.AddTextBox( this, 380, 140, 25, 190, 50 );
			
			//Required Field			
			m_lblRequired				= CUtilities.AddRequiredFieldLabel( this, 400, 135 );
			
			//OK Button
			m_btnOK_Click				= CUtilities.AddButton( this, this, "OK" , 420, 30, 35, 115 );
			//Cancel Button
			m_btnCancel_Click			= CUtilities.AddButton( this, this, "Cancel" , 420, 200, 35, 115 );
			
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
	}

	
	
	// --------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------
	// WindowListener
	// --------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------
	


	// --------------------------------------------------------------------------------
	// Name: WindowOpened
	// Abstract:load the form controls with the values for record from database
	// --------------------------------------------------------------------------------
	@Override
	public void windowOpened( WindowEvent weSource )
	{
		try
		{
			boolean blnResult = false;
			
			//We are busy
			CUtilities.SetBusyCursor( this, true );
			
			//Load the State
			blnResult = CDatabaseUtilities.LoadComboBoxFromDatabase( "TStates", "intStateID", 
																	"strState", m_cmbState );
			
			//Did it work
			if( blnResult == false )
			{
				//No Warn user and ...
				CMessageBox.Show( this, "Unable to load the Statea list.\n" + 
										"The Form will now close.", "Edit The Player",
										enuIconType.Error );
				
				//Close
				setVisible( false );
			}
			
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		finally
		{
			//We are NOT busy
			CUtilities.SetBusyCursor( this, false );
		}
		
	}

	
	
	//Don't care
	public void windowActivated( WindowEvent weSource ) { }
	public void windowClosed( WindowEvent weSource ) { }
	public void windowClosing( WindowEvent weSource ) { }
	public void windowDeactivated( WindowEvent weSource ) { }
	public void windowDeiconified( WindowEvent weSource ) { }
	public void windowIconified( WindowEvent weSource ) { }

	
	// --------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------
	// Name: Action  Listener
	// --------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------
	
	
	
	// --------------------------------------------------------------------------------
	// Name: ActionPerformed
	// Abstract : Event handler for the control click events
	// --------------------------------------------------------------------------------
	//@Override
	//public void actionPerformed( ActionEvent arg0 )
	@Override
	public void actionPerformed( ActionEvent aeSource )
	{
		try
		{
			//VB.Net Event procedure Names: <Control Name>_<Event Type>
				 if( aeSource.getSource( ) == m_btnOK_Click )							m_btnOK_Click( );
			else if( aeSource.getSource( ) == m_btnCancel_Click )						m_btnCancel_Click( );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
	}


	// --------------------------------------------------------------------------------
	// Name: m_btnOK_Click
	// Abstract : Ok to add to Database
	// --------------------------------------------------------------------------------
	public void m_btnOK_Click( )
	{
		try
		{
			//is this form data good?
			if( IsValidData( ) ==  true )
			{
				//Did it save to the database?
				if( SaveData( ) == true )
				{
					//Yes, Success
					m_blnResult = true;
					
					//Yes, all done
					setVisible(false);
				}
			}
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: IsValidData
	// Abstract : Check All the data and warn the user if its bad
	// --------------------------------------------------------------------------------
	private boolean IsValidData( )
	{
		//Assume data is good
		boolean blnIsValidData = true;
		
		try
		{
			String strErrorMessage = "Please correct the following error(s):\n";
			
			//Trim textboxes
			CUtilities.TrimAllFormTextBoxes( this );
			
			//First Name - required field
			if( m_txtFirstName.getText( ).equals(  "" ) == true )
			{
				strErrorMessage += "-First Name cannot be blank\n";
				blnIsValidData = false;
			}
			
			//Last Name - required field
			if( m_txtLastName.getText( ).equals(  "" ) == true )
			{
				strErrorMessage += "-Last Name cannot be blank\n";
				blnIsValidData = false;
			}
			
			//Zip Code blank  not a required field
			if(m_txtZipCode.getText( ).equals( "" ) == false )
			{
				//No, its Valid format
				if( CUtilities.IsValidZipCode( m_txtZipCode.getText( ) ) == false )
				{
					strErrorMessage += "-ZipCode is invalid\n";
					blnIsValidData = false;
				}
			}
			
			//HomePhoneNumber if not blank then validate
			if(m_txtHomePhoneNumber.getText( ).equals( "" ) == false )
			{
				//No, its Valid format
				if( CUtilities.IsValidPhoneNumber( m_txtHomePhoneNumber.getText( ) ) == false )
				{
					strErrorMessage += "-Home Phone Number is invalid\n";
					blnIsValidData = false;
				}
			}
			
			//Salary if not blank then validate
			if(m_txtSalary.getText( ).equals( "" ) == false )
			{
				//No, its Valid format
				if( CUtilities.IsCurrency( m_txtSalary.getText( ) ) == false )
				{
					strErrorMessage += "-Salary is invalid\n";
					blnIsValidData = false;
				}
			}
			
			//DateOfBirth if not blank then validate
			if(m_txtDateOfBirth.getText( ).equals( "" ) == false )
			{
				//No, its Valid format
				if( CUtilities.IsValidDate( m_txtDateOfBirth.getText( ) ) == false )
				{
					strErrorMessage += "-Date Of Birth is invalid\n";
					blnIsValidData = false;
				}
			}
			
			//bad Data
			if( blnIsValidData == false )
			{
				//Yes, warn the user
				CMessageBox.Show( this, strErrorMessage, getTitle( ) + "Error", enuIconType.Warning );
			}
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return blnIsValidData;
	}
	
	
		
	// --------------------------------------------------------------------------------
	// Name: Save Data
	// Abstract : Get the data off of the form and save it in the database
	// --------------------------------------------------------------------------------
	private boolean SaveData( )
	{
		boolean blnResult = false;
		
		try
		{
			String strSalary = "";
			
			//make a suitcase for moving data
			udtPlayerType udtNewPlayer = new CUserDataTypes( ).new udtPlayerType( );
			
			//Load suitcase with data from 
			udtNewPlayer.intPlayerID 			= 0; // don't know it yet so set it to 0
			udtNewPlayer.strFirstName  			= m_txtFirstName.getText( );
			udtNewPlayer.strMiddleName  		= m_txtMiddleName.getText( );
			udtNewPlayer.strLastName 			= m_txtLastName.getText( );
			udtNewPlayer.strStreetAddress		= m_txtStreetAddress.getText( );
			udtNewPlayer.strCity  				= m_txtCity.getText( );
			udtNewPlayer.intStateID  			= m_cmbState.GetSelectedItemValue( );
			udtNewPlayer.strZipCode 			= m_txtZipCode.getText( );
			udtNewPlayer.strHomePhoneNumber 	= m_txtHomePhoneNumber.getText( );
			if( m_txtSalary.getText( ).equals(  ""  ) == false )
			{
				//remove dollar signs and commas
				strSalary = m_txtSalary.getText( );
				strSalary = strSalary.replace( "$", "");
				strSalary = strSalary.replace(",", "" );
				udtNewPlayer.bdecSalary = new BigDecimal ( strSalary );
			}
			else
			{
				udtNewPlayer.bdecSalary = new BigDecimal ( 0 );
			}
			
			if( m_txtDateOfBirth.getText( ).equals(  ""  ) == false )
			{
				udtNewPlayer.sdtmDateOfBirth = CUtilities.ConvertStringToSQLDate(m_txtDateOfBirth.getText( ) );
			}
			else
			{
				udtNewPlayer.sdtmDateOfBirth = CUtilities.ConvertStringToSQLDate( "1800/01/01" );
			}
			
			//We are busy
			CUtilities.SetBusyCursor( this, true );
			
			//Try to save the data
			blnResult = CDatabaseUtilities.AddPlayerToDatabase( udtNewPlayer );	
			
			//Did it work?
			if( blnResult == true )
			{
				//Yes, save the new Player ID
				m_intNewPlayerID = udtNewPlayer.intPlayerID;
			}
		}
		catch ( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		finally
		{
			//We are not busy
			CUtilities.SetBusyCursor( this, false );
		}
		
		return blnResult;
		
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: m_btnCancel_Click
	// Abstract : Cancel Modal Screen
	// --------------------------------------------------------------------------------
	public void m_btnCancel_Click( )
	{
		try
		{
			setVisible( false ) ;
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: GetResult
	// Abstract : Get Result flag indicating if the edit was successful
	// --------------------------------------------------------------------------------
	public boolean GetResult( )
	{
		try
		{
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return m_blnResult;
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: GetNewInformation
	// Abstract : Get the new information from the form
	// --------------------------------------------------------------------------------
	public udtPlayerType GetNewInformation( )
	{
		udtPlayerType udtPlayer = null;
		
		try
		{
			udtPlayer = new CUserDataTypes( ).new udtPlayerType( );
			
			udtPlayer.intPlayerID = m_intNewPlayerID;
			udtPlayer.strFirstName = m_txtFirstName.getText( );
			udtPlayer.strMiddleName = m_txtMiddleName.getText( );
			udtPlayer.strLastName = m_txtLastName.getText( );
			udtPlayer.strStreetAddress = m_txtStreetAddress.getText( );
			udtPlayer.strCity = m_txtCity.getText( );
			//udtPlayer.strState = m_txtState.getText( );
			udtPlayer.strZipCode = m_txtZipCode.getText( );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return udtPlayer;
	}
}
	

































