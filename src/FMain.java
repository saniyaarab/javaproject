// --------------------------------------------------------------------------------
// Name: Saniya Arab
// Class: IT-161 Java #1
// Abstract: This is a class FMain for Homework 17 - DB - Players
// --------------------------------------------------------------------------------

// --------------------------------------------------------------------------------
// Import
// --------------------------------------------------------------------------------
import java.awt.*; 
import java.awt.event.*;
import javax.swing.*;
import Player.DManagePlayers;
import TeamPlayers.DAssignTeamPlayers;
import Teams.DManageTeams;
import Utilities.*;
import Utilities.CMessageBox.enuIconType;
import Utilities.CUserDataTypes.udtTeamType;

@SuppressWarnings("serial")
public class FMain extends JFrame implements ActionListener,
											 WindowListener
{
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	// Controls
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	//Main Menu
	private JMenuBar m_mbMainMenu							= null;
	//File
	private JMenu m_mnuFile									= null;
		private JMenuItem m_mniFileExit						= null;
	//Manage
	private JMenu m_mnuTools								= null;
		private JMenuItem m_mniToolManageTeams					= null;
		private JMenuItem m_mniToolAssignTeamPlayers			= null;
		private JMenuItem m_mniToolManagePlayers				= null;
	//Help
	private JMenu m_mnuHelp 								= null;
		private JMenuItem m_mniHelpAbout 					= null;
		
	private JPanel m_panManage 								= null;
	private JButton m_btnManageTeams						= null;
	private JButton m_btnManagePlayers						= null;
	private JButton m_btnAssignTeamPlayers					= null;
	
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	// Properties - never make public
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	
	
	
	// --------------------------------------------------------------------------------
	// Name: main
	// Abstract: This is where the program starts
	// --------------------------------------------------------------------------------
	public static void main( String astrCommanLine[])
	{
		try
		{
			FMain frmMain = new FMain( );
			
			frmMain.setVisible( true );
			
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
	}

	
	
	// --------------------------------------------------------------------------------
	// PName: FMain
	// Abstract: Abstract: Constructor
	// --------------------------------------------------------------------------------
	public FMain( )
	{
		try
		{	
			//Initialize this frame
			Initialize( );
			
			//Add Build frame
			BuildMenu( );
			
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
	// Name: Initialize .
	// Abstract: Center, set the title, etc
	// --------------------------------------------------------------------------------
	private void Initialize()
	{
		try
		{
			int intWidth = 310;
			int intHeight = 300;
			
			//Title
			setTitle( " Homework 17 - Assign Players" );
			
			//Size
			setSize( intWidth, intHeight);
			
			//Center Screen
			CUtilities.CenterScreen( this );
			
			//No resize
			setResizable( false );
			
			//Exit application on close 
			setDefaultCloseOperation(EXIT_ON_CLOSE)	;
			
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
	// Name: BuildMenu
	// Abstract : Build Menu for this frame
	// --------------------------------------------------------------------------------
	private void BuildMenu( )
	{
		try
		{
			//Main menu
			m_mbMainMenu = CUtilities.AddMenuBar( this );
			
			//File
			m_mnuFile = CUtilities.AddMenu( m_mbMainMenu, "File", 'F' );
			
				//Exit
				m_mniFileExit = CUtilities.AddMenuItem( m_mnuFile, this, "Exit", 'X', 'X' );
			
			//Tools
			m_mnuTools = CUtilities.AddMenu( m_mbMainMenu, "Tools", 'S' );
			
				//Manage Teams
				m_mniToolManageTeams = CUtilities.AddMenuItem( m_mnuTools, this, "Manage Teams", 'T' );
				
				//Assign Team Players
				m_mniToolAssignTeamPlayers = CUtilities.AddMenuItem( m_mnuTools, this, "Assign Team Players", 'A' );
						
				//Manage Players
				m_mniToolManagePlayers = CUtilities.AddMenuItem( m_mnuTools, this, "Manage Players", 'P');
				
				
				
			//Help
			m_mnuHelp = CUtilities.AddMenu( m_mbMainMenu, "Help", 'H' );
			
				//About
				m_mniHelpAbout = CUtilities.AddMenuItem( m_mnuHelp, this, "About", 'A' );
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
			
			//Panel
			m_panManage							= CUtilities.AddPanel( this, 20, 20 , 200, 250, "Manage/Assign");
												
			//Manage Teams button
			m_btnManageTeams					= CUtilities.AddButton( m_panManage, this, "Manage Teams" ,'T', 35, 25, 35, 200 );
			
			//Manage Teams button
			m_btnAssignTeamPlayers				= CUtilities.AddButton( m_panManage, this, "Assign Team Players" , 'A', 90, 25, 35, 200 );
			
			//Manage Teams button
			m_btnManagePlayers					= CUtilities.AddButton( m_panManage, this, "Manage Players" ,'P', 145, 25, 35, 200 );
			
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
			// We are busy
			CUtilities.SetBusyCursor(  this, true );
			
			//Can we connect to the database
			if( CDatabaseUtilities.OpenDatabaseConnection( ) == false )
			{
				//no, warn the user...
				CMessageBox.Show( this, "Database connection error.\n" + 
								  "The application will close.\n", 
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
	
	
	
	// --------------------------------------------------------------------------------
	// Name: windowClosed
	// Abstract : The Window is Closed.
	// ------------------------------------------------------------------------------
	public void windowClosed( WindowEvent weSource )
	{
		try
		{
			CleanUp( );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: windowClosing
	// Abstract : The Window is Closing.
	// ------------------------------------------------------------------------------
	public void windowClosing( WindowEvent weSource )
	{
		try
		{
			CleanUp( );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: CleanUp
	// Abstract : To cleanup both WindowClosed and WindowClosing events
	// ------------------------------------------------------------------------------
	public void CleanUp( ) 
	{
		try
		{
			CDatabaseUtilities.CloseDatabaseConnection( );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	//Don't Care
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
				 if( aeSource.getSource( ) == m_btnManageTeams )						btnManageTeams_Click( );
			else if( aeSource.getSource( ) == m_btnAssignTeamPlayers )					btnAssignTeamPlayers_Click( );
			else if( aeSource.getSource( ) == m_btnManagePlayers )						btnManagePlayers_Click( );
			else if(aeSource.getSource( ) == m_mniFileExit )							btnFileExit_Click( );
			else if(aeSource.getSource( ) == m_mniToolManageTeams )						m_mniToolManageTeams_Click( );
			else if(aeSource.getSource( ) == m_mniToolAssignTeamPlayers )				m_mniToolAssignTeamPlayers_Click( );
			else if(aeSource.getSource( ) == m_mniToolManagePlayers )					m_mniToolManagePlayers_Click( );
			else if(aeSource.getSource( ) == m_mniHelpAbout )							m_mniHelpAbout_Click( );
				 
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
	}

	
	
	// --------------------------------------------------------------------------------
	// Name: btnFileExit_Click
	// Abstract: Exit Application
	// --------------------------------------------------------------------------------
	private void btnFileExit_Click()
	{
		try
		{
			this.dispose( );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
		
		
	}



	// --------------------------------------------------------------------------------
	// Name: m_mniManageTeams_Click
	// Abstract : Button click to manage teams
	// --------------------------------------------------------------------------------
	public void m_mniToolManageTeams_Click( )
	{
		try
		{
			ManageTeams( );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
	}



	// --------------------------------------------------------------------------------
	// Name: btnManageTeams_Click
	// Abstract : Button click to manage teams
	// --------------------------------------------------------------------------------
	public void btnManageTeams_Click( )
	{
		try
		{
			ManageTeams( );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
	}



	// --------------------------------------------------------------------------------
	// Name: ManageTeams
	// Abstract: Manage teams 
	// --------------------------------------------------------------------------------
	private void ManageTeams()
	{
		try
		{
			DManageTeams dlgManageTeams = null;
			
			//Make Instance
			dlgManageTeams = new DManageTeams( this );
			
			//Show Modally
			dlgManageTeams.setVisible( true );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
	}

	
	// --------------------------------------------------------------------------------
	// Name: m_mniAssignTeamPlayers_Click
	// Abstract : Button click to manage Players
	// --------------------------------------------------------------------------------
	public void m_mniToolAssignTeamPlayers_Click( )
	{
		try
		{
			AssignTeamPlayers( );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
	}



	// --------------------------------------------------------------------------------
	// Name: btnAssignTeamPlayers_Click
	// Abstract : Button click to manage Players
	// --------------------------------------------------------------------------------
	public void btnAssignTeamPlayers_Click( )
	{
		try
		{
			AssignTeamPlayers( );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
	}
	
	
	// --------------------------------------------------------------------------------
	// Name: AssignTeamPlayers
	// Abstract : Button click to manage Players
	// --------------------------------------------------------------------------------
	public void AssignTeamPlayers( )
	{
		try
		{
			DAssignTeamPlayers dlgAssignTeamPlayers = null;
			
			//Make Instance
			dlgAssignTeamPlayers = new DAssignTeamPlayers( this );
			
			//Show Modally
			dlgAssignTeamPlayers.setVisible( true );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
	}
	
	
	
	// --------------------------------------------------------------------------------
	// Name: m_mniManagePlayers_Click
	// Abstract : Button click to manage Players
	// --------------------------------------------------------------------------------
	public void m_mniToolManagePlayers_Click( )
	{
		try
		{
			ManagePlayers( );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
	}



	// --------------------------------------------------------------------------------
	// Name: btnManagePlayers_Click
	// Abstract : Button click to manage Players
	// --------------------------------------------------------------------------------
	public void btnManagePlayers_Click( )
	{
		try
		{
			ManagePlayers( );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
	}



	// --------------------------------------------------------------------------------
	// Name: ManagePlayers
	// Abstract : Button click to manage Players
	// --------------------------------------------------------------------------------
	public void ManagePlayers( )
	{
		try
		{
			DManagePlayers dlgManagePlayers = null;
			
			//Make Instance
			dlgManagePlayers = new DManagePlayers( this );
			
			//Show Modally
			dlgManagePlayers.setVisible( true );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
	}



	// --------------------------------------------------------------------------------
	// Name: m_mniHelpAbout_Click
	// Abstract: Exit Application
	// --------------------------------------------------------------------------------
	private void m_mniHelpAbout_Click( )
	{
		try
		{
			HelpAbout( );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
		
		
	}
	
	
	// --------------------------------------------------------------------------------
	// Name: HelpAbout
	// Abstract: Exit Application
	// --------------------------------------------------------------------------------
	private void HelpAbout( )
	{
		try
		{
			CMessageBox.Show( this , "My first Java database Application \nby Patrick Callahan\n"
									+ "\nTeam And Players...Never Dies! \nIt Just Gets better with each version.", 
									"About", enuIconType.Information );
		}
		catch( Exception excError )
		{
			//Display Error Message
			CUtilities.WriteLog( excError );
		}	
		
		
	}

}
































