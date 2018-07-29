// --------------------------------------------------------------------------------
// Name: <Your Name>
// Class: IT-161 Java #1
// Abstract: This is a class FMain for Homework 16 - DB - Players
// --------------------------------------------------------------------------------

//--------------------------------------------------------------------------------
//Package
//--------------------------------------------------------------------------------
package Teams;

// --------------------------------------------------------------------------------
// Import
// --------------------------------------------------------------------------------
import java.awt.*; 
	import java.awt.event.*;
	import javax.swing.*;
	import Utilities.*;
	import Utilities.CMessageBox.enuIconType;
	import Utilities.CUserDataTypes.udtTeamType;

@SuppressWarnings("serial")
public class DEditTeam extends JDialog implements ActionListener, WindowListener

{
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	// Controls
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	private JLabel m_lblTeam		 				= null;
	private JLabel m_lblMascot		 				= null;
	private CTextBox m_txtTeam						= null;
	private CTextBox m_txtMascot					= null;
	private JButton m_btnOK_Click					= null;
	private JButton m_btnCancel_Click				= null;
	
	
	
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	// Properties - never make public
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	private int m_intTeamToEditID = 0;
	private boolean m_blnResult = false;
	
	
	// --------------------------------------------------------------------------------
	// Name: DEditTeam
	// Abstract: Constructor
	// --------------------------------------------------------------------------------
	public DEditTeam( JDialog dlgParent, int intTeamToEditID )
	{
		super( dlgParent, true ); //true = modal
		
		try
		{	
			//Save ID for the loading and saving
			m_intTeamToEditID = intTeamToEditID;
			
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
			int intWidth = 290;
			int intHeight = 190;
			
			//Title
			setTitle( " Edit Team" );
			
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
			
			//Teams Label
			m_lblTeam					= CUtilities.AddLabel( this, "Teams:", 20, 10 );
			
			//Teams Textbox
			m_txtTeam					= CUtilities.AddTextBox( this, 20, 80, 25, 190, 50 );
			
			//Mascot Label
			m_lblMascot					= CUtilities.AddLabel( this, "Mascot:", 65, 10 );
			
			//Mascot Textbox
			m_txtMascot					= CUtilities.AddTextBox( this, 65, 80, 25, 190, 50 );
			
			//OK Button
			m_btnOK_Click				= CUtilities.AddButton( this, this, "OK" , 100, 10, 35, 115 );
			
			//Cancel Button
			m_btnCancel_Click			= CUtilities.AddButton( this, this, "Cancel" , 100, 150, 35, 115 );
			
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
			udtTeamType udtTeam = new CUserDataTypes( ).new udtTeamType( );
			boolean blnResult = false;
			
			//Which Team to load?
			udtTeam.intTeamID = m_intTeamToEditID;
			
			//We are busy
			CUtilities.SetBusyCursor( this, true );
			
			//Get Values
			blnResult = CDatabaseUtilities.GetTeamInformationFromDatabase( udtTeam );
			
			//Did it work
			if( blnResult == true )
			{
				//Yes load form with values
				m_txtTeam.setText( udtTeam.strTeam );
				m_txtMascot.setText( udtTeam.strMascot );
			}
			else
			{
				//No Warn user and ...
				CMessageBox.Show( this, "Unable to load team information.\n" + "The Form will now close.", "Edit The Team", enuIconType.Error );
				
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
					//yes , Success
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
			
			//Team
			if( m_txtTeam.getText( ).equals(  "" ) == true )
			{
				strErrorMessage += "-Team cannot be blank\n";
				blnIsValidData = false;
			}
			
			//Mascot
			if( m_txtMascot.getText( ).equals(  "" ) == true )
			{
				strErrorMessage += "-Mascot cannot be blank\n";
				blnIsValidData = false;
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
			//make a suitcase for moving data
			udtTeamType udtNewTeam = new CUserDataTypes( ).new udtTeamType( );
			
			//Load suitcase with data from 
			udtNewTeam.intTeamID = m_intTeamToEditID; // don't know it yet so set it to 0
			udtNewTeam.strTeam  = m_txtTeam.getText( );
			udtNewTeam.strMascot = m_txtMascot.getText( );
			
			//We are busy
			CUtilities.SetBusyCursor( this, true );
			
			//Try to save the data
			blnResult = CDatabaseUtilities.EditTeamInDatabase( udtNewTeam );		
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
	public udtTeamType GetNewInformation( )
	{
		udtTeamType udtTeam = null;
		
		try
		{
			udtTeam = new CUserDataTypes( ).new udtTeamType( );
			
			udtTeam.intTeamID = m_intTeamToEditID;
			udtTeam.strTeam = m_txtTeam.getText( );
			udtTeam.strMascot = m_txtMascot.getText( );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return udtTeam;
	}

}
	




































