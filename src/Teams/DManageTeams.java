// --------------------------------------------------------------------------------
// Name: Saniya Arab
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
public class DManageTeams extends JDialog implements ActionListener,
											 WindowListener
{
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	// Controls
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	private JLabel m_lblTeams		 				= null;
	private CListBox m_lstTeams						= null;
	private JButton m_btnAdd						= null;
	private JButton m_btnEdit						= null;
	private JButton m_btnDelete						= null;
	private JButton m_btnUndelete					= null;
	private JButton m_btnClose						= null;
	private JCheckBox m_chkShowDeleted				= null;
	

	
	
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
	// PName: Manage Teams
	// Abstract: Abstract: Constructor
	// --------------------------------------------------------------------------------
	public DManageTeams(JFrame frmParent )
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
			int intWidth = 522;
			int intHeight = 400;
			
			//Title
			setTitle( " Homework 17 - Manage Teams" );
			
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
			this.setLayout( null );
			
			//Teams Label
			m_lblTeams					= CUtilities.AddLabel( this, "Teams:", 14, 20 );
			
			//Teams List
			m_lstTeams					= CUtilities.AddListBox( this, 35, 20, 250, 315 );
			
			//Add
			m_btnAdd					= CUtilities.AddButton( this, this, "Add" , 45, 370, 35, 115 );
			
			//Edit
			m_btnEdit					= CUtilities.AddButton( this, this, "Edit" , 130, 370, 35, 115 );
			
			//Delete
			m_btnDelete					= CUtilities.AddButton( this, this, "Delete" , 'D', 215, 370, 35, 115 );
			
			//UnDelete
			m_btnUndelete				= CUtilities.AddButton( this, this, "Undelete" , 'U', 215, 370, 35, 115 );
			m_btnUndelete.setVisible( false) ;
			
			//Delete CheckBox
			m_chkShowDeleted			=CUtilities.AddCheckBox( this, this, "Show Deleted", 280, 16 );
			
			//Close
			m_btnClose					= CUtilities.AddButton( this, this, "Close" , 315, 50, 35, 250 );
			
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
			
			//Load the team list
			blnResult = LoadTeamsList( true );
			
			//Did it work
			if( blnResult == false )
			{
				//no, warn the user...
				CMessageBox.Show( this, "Unable to load the teams list.\n" + 
								  "The Form will close.\n", 
								  getTitle( ) + " Error", enuIconType.Error );
				
				//and Close the form/Application
				setVisible( false );
			}
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: LoadTeamsList
	// Abstract : Load the Teams List from the database
	// ------------------------------------------------------------------------------
	public boolean LoadTeamsList( boolean blnActive )
	{
		boolean blnResult = false;
		
		try
		{
			String strTable = "VActiveTeams" ;
			
			//Deleted Teams?
			if( blnActive == false )
			{
				//Yes
				strTable = "VInactiveTeams";
			}
			
			// We are busy
			CUtilities.SetBusyCursor(  this, true );
			
			//Load the team list
			blnResult = CDatabaseUtilities.LoadListBoxFromDatabase( strTable, "intTeamID", "strTeam", m_lstTeams);
			
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
		
		return blnResult;
	}	

	
	//Don't Care
	public void windowClosing( WindowEvent weSource ) { }
	public void windowClosed( WindowEvent weSource ) { }
	public void windowActivated( WindowEvent weSource ) { }
	public void windowDeactivated( WindowEvent weSource ) { }
	public void windowDeiconified( WindowEvent weSource ) { }
	public void windowIconified( WindowEvent weSource ) { }
	
	
	
	// --------------------------------------------------------------------------------
	// Name: Action  Listener
	// Abstract : Event handler for the control click events
	// ------------------------------------------------------------------------------

	
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
				 if( aeSource.getSource( ) == m_btnAdd )						btnAdd_Click( );
			else if( aeSource.getSource( ) == m_btnEdit )						btnEdit_Click( );
			else if( aeSource.getSource( ) == m_btnDelete )						btnDelete_Click( );
			else if( aeSource.getSource( ) == m_btnUndelete )		 			btnUndelete_Click( );
			else if( aeSource.getSource( ) == m_chkShowDeleted )				chkShowDeleted_Click( );
			else if( aeSource.getSource( ) == m_btnClose )						btnClose_Click( );
		
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: btnAdd_Click
	// Abstract : load a connection to the database
	// --------------------------------------------------------------------------------
	public void btnAdd_Click( )
	{
		try
		{
			DAddTeam dlgAddTeam = null;
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
			}
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: btnEdit_Click
	// Abstract : load a connection to the database
	// --------------------------------------------------------------------------------
	public void btnEdit_Click( )
	{
		try
		{
			int intSelectedListIndex = 0;
			CListItem liSelectedTeam = null;
			int intSelectedTeamID = 0;
			DEditTeam dlgEditTeam = null;
			udtTeamType udtNewTeam = null;
			
			//Get the selected index from the list
			intSelectedListIndex = m_lstTeams.GetSelectedIndex( );
			
			//is something selected?
			if( intSelectedListIndex < 0 )
			{
				//No, warn the user
				CMessageBox.Show( this, "You must select a Team to edit", "Edit Team Error");
			}
			else
			{
				//Yes, so get the selected list item ID and name
				liSelectedTeam = m_lstTeams.GetSelectedItem( );
				intSelectedTeamID = liSelectedTeam.GetValue( );
				
				//Make instance
				dlgEditTeam = new DEditTeam(this, intSelectedTeamID );
				
				//Display Modally
				dlgEditTeam.setVisible( true );
				
				//Did it work
				if( dlgEditTeam.GetResult( )== true )
				{
					//Yes, get the new team information
					udtNewTeam = dlgEditTeam.GetNewInformation( );
					
					//Remove old record
					m_lstTeams.RemoveAt( intSelectedListIndex );
					
					//Add re-Add so it gets sorted correctly (true = select)
					m_lstTeams.AddItemToList( udtNewTeam.intTeamID, udtNewTeam.strTeam, true );
				}
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
	
	
	
	// --------------------------------------------------------------------------------
	// Name: btnDelete_Click
	// Abstract : Update status of team to Inactive
	// --------------------------------------------------------------------------------
	public void btnDelete_Click( )
	{
		try
		{
			int intSelectedListIndex = 0;
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
				CMessageBox.Show( this, "You must select a Team to delete", "Delete Team Error",enuIconType.Warning);
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
	
	
	
	// --------------------------------------------------------------------------------
	// Name: btnUndelete_Click
	// Abstract : Update status of team to Active
	// --------------------------------------------------------------------------------
	public void btnUndelete_Click( )
	{
		try
		{
			int intSelectedListIndex = 0;
			CListItem liSelectedTeam = null;
			int intSelectedTeamID = 0;
			boolean blnResult = false;
			
			//Get the selected index from the list
			intSelectedListIndex = m_lstTeams.GetSelectedIndex( );
			
			//is something selected?
			if( intSelectedListIndex < 0 )
			{
				//No, warn the user
				CMessageBox.Show( this, "You must select a Team to Undelete", "Undelete Team Error",enuIconType.Warning );
			}
			else
			{
				//Yes, so get the selected list item ID and name
				liSelectedTeam = m_lstTeams.GetSelectedItem( );
				intSelectedTeamID = liSelectedTeam.GetValue( );
				
				//Yes, We are busy
				CUtilities.SetBusyCursor( this, true );
				
				//Attempt to Undelete
				blnResult = CDatabaseUtilities.UndeleteTeamFromDatabase( intSelectedTeamID );
				
				//Did it work
				if( blnResult == true )
				{
					//Yes, remove record. Next closes record automatically selected
					m_lstTeams.RemoveAt( intSelectedListIndex );
				}
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
	
	
	
	// --------------------------------------------------------------------------------
	// Name: chkShowDeleted_Click
	// Abstract : Toggle between active and inactive teams.
	// --------------------------------------------------------------------------------
	public void chkShowDeleted_Click( )
	{
		try
		{
			//Show Deleted?
			if( m_chkShowDeleted.isSelected( ) == false )
			{
				//No
				LoadTeamsList( true );
				m_btnAdd.setEnabled(  true );
				m_btnEdit.setEnabled( true );
				m_btnDelete.setVisible( true );
				m_btnUndelete.setVisible( false );
			}
			else
			{
				//Yes
				LoadTeamsList( false );
				m_btnAdd.setEnabled( false );
				m_btnEdit.setEnabled( false );
				m_btnDelete.setVisible( false );
				m_btnUndelete.setVisible( true );
			}
			
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
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
































