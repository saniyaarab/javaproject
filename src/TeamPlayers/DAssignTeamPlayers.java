// --------------------------------------------------------------------------------
// Name: Saniya Arab
// Class: IT-161 Java #1
// Abstract: This is a class FMain for Homework 17 - DB - Assign Team Players
// --------------------------------------------------------------------------------

//--------------------------------------------------------------------------------
//Package
//--------------------------------------------------------------------------------
package TeamPlayers;

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
public class DAssignTeamPlayers extends JDialog implements ActionListener,
											 WindowListener, ItemListener
{
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	// Controls
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	private JLabel m_lblTeam		 				= null;
	private JLabel m_lblSelectedPlayers				= null;
	private JLabel m_lblAvailablePlayers			= null;
	private CListBox m_lstAvailablePlayers			= null;
	private CListBox m_lstSelectedPlayers			= null;
	private CComboBox m_cmbTeam						= null;
	private JButton m_btnAll						= null;
	private JButton m_btnAdd						= null;
	private JButton m_btnRemove						= null;
	private JButton m_btnNone						= null;
	private JButton m_btnClose						= null;
	private JPanel m_panPlayers						= null;

	
	
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	// Properties - never make public
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	// Methods
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	
	// --------------------------------------------------------------------------------
	// PName: Assign Team Players
	// Abstract: Abstract: Constructor
	// --------------------------------------------------------------------------------
	public DAssignTeamPlayers(JFrame frmParent )
	{
		super( frmParent, true ); //true = modal
		
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
			int intWidth = 635;
			int intHeight = 420;
			
			//Title
			setTitle( " Homework 17 - Assign team Players" );
			
			//Size
			setSize( intWidth, intHeight);
			
			//Center Screen
			CUtilities.CenterOwner( this );
			
			//No resize
			setResizable( false );
			
			//Exit application on close 
			//setDefaultCloseOperation(EXIT_ON_CLOSE)	; 
			
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
	// Name: AddControls
	// Abstract : Add all the controls to the Frame
	// --------------------------------------------------------------------------------
	private void AddControls( )
	{
		try
		{
			//Clear layout manager so we can manually size and position controls
			CUtilities.ClearLayoutManager( this );
			
			//Height : 420 Width : 635
			//Teams 
			m_lblTeam					= CUtilities.AddLabel( this, "Team:", 19, 20 );
			m_cmbTeam 					= CUtilities.AddComboBox( this, this, 18, 60, 20, 200 );
			
			//Group Box (titled panel)
			m_panPlayers 				= CUtilities.AddPanel( this, 50, 20, 275, 590, "Players" );
			
			//Selected Players
			m_lblSelectedPlayers		= CUtilities.AddLabel( m_panPlayers, "Selected", 25, 25 );
			m_lstSelectedPlayers		= CUtilities.AddListBox( m_panPlayers, 42, 25, 205, 200 );
			//Fix: m_lstSelectedPlayers		= CUtilities.AddListBox( m_panPlayers, this, 42, 25, 205, 200 );
			
			//m_btnnAll
			m_btnAll					= CUtilities.AddButton( m_panPlayers, this, "<< All", 'A', 65, 245, 25, 100 );
			
			//Add
			m_btnAdd					= CUtilities.AddButton( m_panPlayers, this, "< Add" , 'D',  110, 245, 25, 100 );
			
			//Remove
			m_btnRemove					= CUtilities.AddButton( m_panPlayers, this, "Remove >" , 'R',  155, 245, 25, 100 );
			
			//None
			m_btnNone					= CUtilities.AddButton( m_panPlayers, this, "None >>" ,'N', 200	, 245, 25, 100 );
			
			//Available Players
			m_lblAvailablePlayers		= CUtilities.AddLabel( m_panPlayers, "Available", 25, 365 );
			m_lstAvailablePlayers		= CUtilities.AddListBox( m_panPlayers, 42, 365, 205, 200 );
			//m_lstAvailablePlayers		= CUtilities.AddListBox( m_panPlayers, this, 42, 365, 205, 200 );
			
			//m_btnClose
			m_btnClose					= CUtilities.AddButton( this, this, "Close" ,'C', 345, 217, 30, 200 );
			
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	// WndowListener
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	
	
	
	// --------------------------------------------------------------------------------
	// Name: windowOpened
	// Abstract : The Window is Opened. Triggered by setVariable( true ).
	// ------------------------------------------------------------------------------
	public void windowOpened( WindowEvent weSource )
	{
		try
		{
			boolean blnResult = false;
			
			// We are busy
			CUtilities.SetBusyCursor(  this, true );
			
			//Load the team list
			blnResult = CDatabaseUtilities.LoadComboBoxFromDatabase( "VActiveTeams", "intTeamID", "strTeam", m_cmbTeam);
			
			//Did it work
			if( blnResult == false )
			{
				//no, warn the user...
				CMessageBox.Show( this, "Database connection error.\n" + 
								  "The Form will close.\n", 
								  getTitle( ) + " Error", enuIconType.Error );
				
				//and Close the form/Application
				this.dispose( );
			}
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
		finally
		{
			//we are not busy
			CUtilities.SetBusyCursor( this, false );
		}
	}
	

	//Don't Care
	public void windowClosing( WindowEvent weSource ) { }
	public void windowClosed( WindowEvent weSource ) { }
	public void windowActivated( WindowEvent weSource ) { }
	public void windowDeactivated( WindowEvent weSource ) { }
	public void windowDeiconified( WindowEvent weSource ) { }
	public void windowIconified( WindowEvent weSource ) { }
	
	
	//---------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------
	// Item  Listener
	// --------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------
	
	
	
	// --------------------------------------------------------------------------------
	// Name: itemStateChanged
	// Abstract : Selected Item Index in combo box changed
	// --------------------------------------------------------------------------------
	@Override
	public void itemStateChanged( ItemEvent ieSource )
	{
		try
		{
			//cmb Teams?
			if( ieSource.getSource( ) == m_cmbTeam )
			{
				//Selected Team
				if( ieSource.getStateChange( ) == ItemEvent.SELECTED )
				{
					LoadTeamPlayers( );
				}
			}
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
	}
	
	
	
	// --------------------------------------------------------------------------------------
	// Name: LoadTeamPlayers
	// Abstract : Selected Index for team combo box changed
	// --------------------------------------------------------------------------------------
	public void LoadTeamPlayers(  )
	{
		try
		{
			int intTeamID = 0;
			
			//Get the Selected team ID
			intTeamID = m_cmbTeam.GetSelectedItemValue( );
			
			//Is a team selected ( list could be empty)?
			if( intTeamID  > 0 )
			{
				//Yes, we are busy
				CUtilities.SetBusyCursor( this,  true );
				
				//Selected Players
				CDatabaseUtilities.LoadListWithPlayersFromDatabase( intTeamID, m_lstSelectedPlayers, true );
				
				//Available Players
				CDatabaseUtilities.LoadListWithPlayersFromDatabase( intTeamID, m_lstAvailablePlayers, false );
				
				//Enable/Disable buttons
				EnableButtons( );
			}
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
	}
	
	
	
	// --------------------------------------------------------------------------------------
	// Name: EnableButtons
	// Abstract : Enable/Disable the add/Remove Buttons
	// --------------------------------------------------------------------------------------
	public void EnableButtons(  )
	{
		try
		{
			//All
			if( m_lstAvailablePlayers.Length( ) > 0 ) 	m_btnAll.setEnabled( true );
			else										m_btnAll.setEnabled( true );
			
			//Add
			if( m_lstAvailablePlayers.Length( ) > 0 ) 	m_btnAdd.setEnabled( true );
			else										m_btnAdd.setEnabled( true );
			
			//Remove
			if( m_lstSelectedPlayers.Length( ) > 0 ) 	m_btnRemove.setEnabled( true );
			else										m_btnRemove.setEnabled( true );
			
			//None
			if( m_lstSelectedPlayers.Length( ) > 0 ) 	m_btnNone.setEnabled( true );
			else										m_btnNone.setEnabled( true );
			
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
	}
	
	
	
	// --------------------------------------------------------------------------------------
	// Name: Action  Listener
	// Abstract : Event handler for the control click events
	// --------------------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract : Event handler for the control click events
	// --------------------------------------------------------------------------------
	@Override
	public void actionPerformed( ActionEvent aeSource )
	{
		try
		{
			//VB.Net Event procedure Names: <Control Name>_<Event Type>
				 if( aeSource.getSource( ) == m_btnAll )						btnAll_Click( );
			else if( aeSource.getSource( ) == m_btnAdd )						btnAdd_Click( );
			else if( aeSource.getSource( ) == m_btnRemove )						btnRemove_Click( );
			else if( aeSource.getSource( ) == m_btnNone )						btnNone_Click( );
			else if( aeSource.getSource( ) == m_btnClose )						btnClose_Click( );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
	}

	
	
	// --------------------------------------------------------------------------------
	// Name: btnAll_Click
	// Abstract : load a connection to the database
	// --------------------------------------------------------------------------------
	public void btnAll_Click( )
	{
		try
		{
			/*DAddTeam dlgAddTeam = null;
			udtTeamType udtNewTeam = null;
			
			//Make Instance
			dlgAddTeam = new DAddTeam( this );
			
			//Show Modally
			dlgAddTeam.setVisible( true );
			
			//Did it work
			if( dlgAddTeam.GetResult( )== true )
			{
				//Yes, get the new team information
				udtNewTeam = dlgAddTeam.GetNewInformation( );
				
				//Add new record to the listbox (true = select)
				m_lstTeams.AddItemToList( udtNewTeam.intTeamID, udtNewTeam.strTeam, true );
			}*/
			CMessageBox.Show( "All button got clicked", "Action Listner All" );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
	}
		
		
		
	
	// --------------------------------------------------------------------------------
	// Name: btnAdd_Click
	// Abstract : Add a Player to the team
	// --------------------------------------------------------------------------------
	public void btnAdd_Click( )
	{
		try
		{
			int intTeamID = 0;
			int intPlayerID = 0;
			boolean blnResult = false;
			
			//We are busy
			CUtilities.SetBusyCursor( this, true );
			
			//Get Team and Player IDs
			intTeamID = m_cmbTeam.GetSelectedItemValue( );
			intPlayerID = m_lstAvailablePlayers.GetSelectedItemValue( );
			
			//Add the player to the team
			blnResult = CDatabaseUtilities.AddPlayerToTeamInDatabase( intTeamID, intPlayerID );
			
			//Did it work
			if( blnResult == true )
			{
				//Yes, get the new team information
				m_lstSelectedPlayers.AddItemToList( m_lstAvailablePlayers.GetSelectedItem( ) );
				
				//Remove from Available List
				m_lstAvailablePlayers.RemoveAt( m_lstAvailablePlayers.GetSelectedIndex ( ) );
			}
			
			//Enable/Disable button
			EnableButtons( );
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
	
	
	
	// --------------------------------------------------------------------------------
	// Name: btnRemove_Click
	// Abstract : load a connection to the database
	// --------------------------------------------------------------------------------
	public void btnRemove_Click( )
	{
		try
		{
			int intTeamID = 0;
			int intPlayerID = 0;
			boolean blnResult = false;
			
			//We are busy
			CUtilities.SetBusyCursor( this, true );
			
			//Get Team and Player IDs
			intTeamID = m_cmbTeam.GetSelectedItemValue( );
			intPlayerID = m_lstSelectedPlayers.GetSelectedItemValue( );
			
			//Remove the player from the team
			blnResult = CDatabaseUtilities.RemovePlayerFromTeamInDatabase( intTeamID, intPlayerID );
			
			//Did it work
			if( blnResult == true )
			{
				//Yes, get the new team information
				m_lstAvailablePlayers.AddItemToList( m_lstSelectedPlayers.GetSelectedItem( ) );
				
				//Remove from Available List
				m_lstSelectedPlayers.RemoveAt( m_lstSelectedPlayers.GetSelectedIndex ( ) );
			}
			
			//Enable/Disable button
			EnableButtons( );
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
	
	
	// --------------------------------------------------------------------------------
	// Name: btnNone_Click
	// Abstract : load a connection to the database
	// --------------------------------------------------------------------------------
	public void btnNone_Click( )
	{
		try
		{
			/*int intSelectedListIndex = 0;
			CListItem liSelectedTeam = null;
			int intSelectedTeamID = 0;
			String strSelectedTeam = "";
			int intConfirm = 0;
			boolean blnResult = false;
			
			//Get the selected index from the list
			intSelectedListIndex = m_lstTeams.GetSelectedIndex( );
			
			//is something selected?
			if( intSelectedListIndex < 0 )
			{
				//No, warn the user
				CMessageBox.Show( this, "You must select a Team to delete", "Delete Team Error");
			}
			else
			{
				//Yes, so get the selected list item ID and name
				liSelectedTeam = m_lstTeams.GetSelectedItem( );
				intSelectedTeamID = liSelectedTeam.GetValue( );
				strSelectedTeam = liSelectedTeam.GetName( );
				
				//Confirm Delete
				if( intConfirm == CMessageBox.intRESULT_YES )
				{
					//Yes, We are busy
					CUtilities.SetBusyCursor( this, true );
					
					//Attempt to delete
					blnResult = CDatabaseUtilities.DeleteTeamFromDatabase( intSelectedTeamID );
					
					//Did it work
					if( blnResult == true )
					{
						//Yes, remove record. Next closes record automatically selected
						m_lstTeams.RemoveAt( intSelectedListIndex );
					}
				}
			}*/
			CMessageBox.Show( "None button got clicked", "Action Listner None" );

		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
	/*	finally
		{
			//We are NOT busy
			CUtilities.SetBusyCursor( this, false );
		}*/
	}
	
	
	// --------------------------------------------------------------------------------
	// Name: btnClose_Click
	// Abstract : load a connection to the database
	// --------------------------------------------------------------------------------
	public void btnClose_Click( )
	{
		try
		{
			//Close the Application
			setVisible(false );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
	}
	
}
































