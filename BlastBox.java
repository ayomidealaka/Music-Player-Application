/** 
 * 
 */

//Ayomide Alaka-Yusuf

import javafx.scene.media.MediaPlayer.Status;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.text.DecimalFormat;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;

//import component
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;

//import for layout
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class BlastBox extends Application {
	//Declare class level components.
	Label lblAvailableTrack, lblPlaylist, lblVolume, lblMusicTime;
	
	Button btnAddMusic, btnRemoveMusic, btnRemoveAllMusic, btnPlayMusic, btnPauseMusic, btnStopMusic;
	
	ListView <String> lstAvailableTrack, lstPlaylist;
	
	Map<String, String>arrayMap = new HashMap<>();
	
	Slider musicSlider, musicVolume;
	
	MediaPlayer mediaPlayer;
	
	Double musicTime;
	
	MenuBar mBar;
	
	File savedFile;
	
	static ArrayList <String> Filelist = new ArrayList <String>();
	ObservableList<File> music = FXCollections.observableArrayList();
	
	//File path for music directory.
	final String directoryPath = "file:./music";
	

	public BlastBox() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void init() {
		mBar = new MenuBar();
		
		Menu fileMenu = new Menu("File");
		Menu fileMenu2 = new Menu("About");
		
		//Add menu items to the file menu.
		MenuItem openItem = new MenuItem("Load MP3 Music Directory...");
		fileMenu.getItems().add(openItem);
		openItem.setOnAction(ae -> loadMusic2());
		
		MenuItem openItem2 = new MenuItem("Load MP3 Music File...");
		fileMenu.getItems().add(openItem2);
		openItem2.setOnAction(ae -> loadMusic());
		
		
		MenuItem aboutItem = new MenuItem("About Software");
		fileMenu2.getItems().add(aboutItem);
		fileMenu2.setOnAction(ae->{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About Software");
			alert.setHeaderText(null);
			alert.setContentText("BlastBox V1.0.0 \nGCD HCI & GUI 2019\nAyomide Alaka Yusuf 2992254");

			alert.showAndWait();
		});
		
		//Add all menus.
		mBar.getMenus().add(fileMenu); //File.
		mBar.getMenus().add(fileMenu2); //About.
		
		
		//Instantiate components with 'new'.
		lblAvailableTrack = new Label("Available Track");
		lblPlaylist = new Label("Playlist");
		lblVolume = new Label("       Volume");
		lblMusicTime= new Label("");
		
		btnAddMusic = new Button("Add music >");
		btnAddMusic.setDisable(true);
		
		btnRemoveMusic = new Button("< Remove");
		btnRemoveMusic.setDisable(true);
		
		btnRemoveAllMusic = new Button("<< Remove All");
		btnRemoveAllMusic.setDisable(true);
		
		btnPlayMusic = new Button("Play");
		btnPlayMusic.setDisable(true);
		
		btnPauseMusic = new Button("Pause");
		btnPauseMusic.setDisable(true);
		
		btnStopMusic = new Button("Stop");
		btnStopMusic.setDisable(true);
		
		lstAvailableTrack = new ListView();
		lstPlaylist = new ListView();
		
		musicSlider = new Slider();
		
		musicVolume = new Slider();
		
		
		
		
		//manage component
		
		btnAddMusic.setMinWidth(100);
		btnRemoveMusic.setMinWidth(100);
		btnRemoveAllMusic.setMinWidth(100);
		btnPlayMusic.setMinWidth(100);
		btnPauseMusic.setMinWidth(100);
		btnStopMusic.setMinWidth(100);
		
		lstAvailableTrack.setMinHeight(150);
		lstPlaylist.setMinHeight(150);
		
		musicSlider.setMinWidth(100);
		musicVolume.setMinWidth(100);
		
		
		
		lstAvailableTrack.setOnMouseClicked(ae-> {
			
			//Get the name that is clicked on.
			lstPlaylist.getSelectionModel().clearSelection();
			String name = lstAvailableTrack.getSelectionModel().getSelectedItem();
			
			//Enables buttons when music is selected.
			btnAddMusic.setDisable(false);
			
			//setting path the value of the selected items name from the mapArray
			String path = arrayMap.get(name);
			
			//Creating the path for the music file and adding it to media
			path = path.replace("\\", "/");
			Media media = new Media(new File(path)
					.toURI().toString());

			System.out.println("---Files Selected---");
			System.out.println(arrayMap.get(name));
			System.out.println();
		});
		
		//Playlist Listview on selection
		lstPlaylist.setOnMouseClicked(ae -> {
					
			//clears the track Listview when the playlist listview is selected
			lstAvailableTrack.getSelectionModel().clearSelection();
			String name = lstPlaylist.getSelectionModel().getSelectedItem();
			
			//Enables buttons Play, Pause, Stop buttons when music is added to playlist.
			btnPlayMusic.setDisable(false);
			btnPauseMusic.setDisable(false);
			btnStopMusic.setDisable(false);
			
			//gets the value of the selected name from the mapArray type
			String path = arrayMap.get(name);
			path = path.replace("\\", "/");
					
			//Assigning the file path to the media
			Media media = new Media(new File(arrayMap.get(name)).toURI().toString());
		
			//Assigning an instance of mediaplayer and giving it a media value
			mediaPlayer = new MediaPlayer(media);
			
			System.out.println("---Files selected---");
			System.out.println(arrayMap.get(name));
		});	
		
		//Set on action for the addtoPlaylist button
		btnAddMusic.setOnAction(ae -> {		
			//Checks to make sure selection is not null
			if(lstAvailableTrack.getSelectionModel().getSelectedItem() != null) {
			String name = lstAvailableTrack.getSelectionModel().getSelectedItem();
			
			btnRemoveMusic.setDisable(false);
			btnRemoveAllMusic.setDisable(false);
						
			//add selected item from the track listview to the playlist listview
			System.out.println("---File Added---");
			System.out.println(name);
			System.out.println();
			lstPlaylist.getItems().add(name);
			}else {
				System.out.println("Error: Please select track from Available Track list.");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Please load File or Please select file from Available Track list.");
				alert.setContentText("Click on File >> Open to load music /Click on music to select...");
				
				alert.show();
				}
		});
		
		//Button Remove from Playlist EventHandler
		btnRemoveMusic.setOnAction(ae -> {
					
			//checks if there is an Item selected in the listview
			if(lstPlaylist.getSelectionModel().getSelectedItem() != null) {
			String name = lstPlaylist.getSelectionModel().getSelectedItem();
						
			//removes the selected item from the listview
			System.out.println("---File Removed---");
			System.out.println(name);
			System.out.println();
			lstPlaylist.getItems().remove(name);
			
			//Disables buttons Play, Pause, Stop, Remove and RemoveAll Buttons when all music is removed.
			if(lstPlaylist.getItems().isEmpty()) {
				btnPlayMusic.setDisable(true);
				btnPauseMusic.setDisable(true);
				btnStopMusic.setDisable(true);
				btnPauseMusic.setDisable(true);
				btnRemoveMusic.setDisable(true);
				btnRemoveAllMusic.setDisable(true);	
				mediaPlayer.stop();
				lblMusicTime.setText(String.valueOf(" "));
				}
			}
		});
		
		//Button Remove from Playlist EventHandler
		btnRemoveAllMusic.setOnAction(ae -> {					
			//removes the selected item from the listview
			lstPlaylist.getItems().clear();
			mediaPlayer.stop();
			
			//Disables buttons Play, Pause, Stop when RemoveAll Buttons is pressed.
			btnPlayMusic.setDisable(true);
			btnPauseMusic.setDisable(true);
			btnStopMusic.setDisable(true);
			System.out.println();
			System.out.println("---All Files Removed From playlist---");
			System.out.println();
		});		
		
		//Handle events on player buttons
		btnPlayMusic.setOnAction(ae -> {
					
				//Plays the media in the music player
				Status status = mediaPlayer.getStatus();
			
				mediaPlayer.stop();
				mediaPlayer.setAutoPlay(true);
				mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
				mediaPlayer.play();
				
						
				//Total duration of the track
				musicTime = mediaPlayer.getTotalDuration().toSeconds();
						
				//Total time to minutes
				String totaltimeinMins = (String.valueOf(new DecimalFormat("#.00")
						.format(mediaPlayer.getTotalDuration().toMinutes())));

				//getting the current time of the music from the music player
				mediaPlayer.currentTimeProperty().addListener((Observable)->{											
				//updating the music current time from the slider
				if(musicSlider.isPressed()){
					mediaPlayer.seek(Duration.seconds((musicSlider.getValue()*(musicTime)/100)));
				}
								
				//sets the value of the slider from the the current music player time
				musicSlider.setValue((mediaPlayer.getCurrentTime().toSeconds()*100)/musicTime);
				System.out.println("Playing music "+mediaPlayer.getCurrentTime().toSeconds());
							
				//updating the current time label with the current time
				lblMusicTime.setText(String.valueOf(new DecimalFormat("Status: Playing " + "#0.00").format(mediaPlayer.getCurrentTime().toMinutes()))+" / "+ totaltimeinMins);
				});	
		});
		
		
		//EventHandler for the pause button
		btnPauseMusic.setOnAction(e -> {
			mediaPlayer.pause();
			//updating the current time label with the current time
			String totaltimeinMins = (String.valueOf(new DecimalFormat("#.00")
					.format(mediaPlayer.getTotalDuration().toMinutes())));

			lblMusicTime.setText(String.valueOf(new DecimalFormat("Status: Paused " + "#0.00").format(mediaPlayer.getCurrentTime().toMinutes()))+" / "+ totaltimeinMins);
			System.out.println("Paused music ");
		});
		
		
		//EventHandler for the stop button
		btnStopMusic.setOnAction(e -> {
			mediaPlayer.stop();
			
			//updating the current time label with the current time
			String totaltimeinMins = (String.valueOf(new DecimalFormat("#.00")
					.format(mediaPlayer.getTotalDuration().toMinutes())));
			lblMusicTime.setText(String.valueOf("Stopped"));
			System.out.println();
			System.out.println("Stopped music");
				});
				
		//updating the volume value of the music player from the volume slider value
		musicVolume.setOnMouseClicked(ae -> {
			Double value = musicVolume.getValue();
			mediaPlayer.setVolume(value);
		});	
	}	
	
	
	public void loadMusic() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("JavaFX Projects");
		
		File selectedFile = chooser.showOpenDialog(null);

			//populating the track listview from the file list
			lstAvailableTrack.getItems().add(selectedFile .getName());
			
			//populating the mapArray with the relative and absolute path
			arrayMap.put(selectedFile .getName(), selectedFile .getAbsolutePath());
			System.out.println(selectedFile .getAbsolutePath());
	} 
	
	
	public void loadMusic2() {
		
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("JavaFX Projects");
		
		File selectedDirectory = chooser.showDialog(null);

		//get all the files from a directory
		File[] fList = selectedDirectory.listFiles();
		System.out.println("---Files in Directory---");
		int no = 1;
	
		for (File file : fList){
			if (file.isFile()){
			//populating the track listview from the file list
			lstAvailableTrack.getItems().add(file.getName());
			
			//populating the mapArray with the relative and absolute path
			arrayMap.put(file.getName(), file.getAbsolutePath());
			System.out.println(file.getAbsolutePath());
			}
		}
	}

	@Override
	public void start(Stage pStage) throws Exception {
		
		//set the Icon.
		pStage.getIcons().add(
				   new Image(
				      BlastBox.class.getResourceAsStream( "headphones.png" ))); 	
		// set the Title.
		pStage.setTitle("MP3 BlastBox");
		
		// set the width and Height.
		pStage.setWidth(500);
		pStage.setHeight(550);
		
		//create a layout.
		GridPane gp = new GridPane();
		BorderPane bp = new BorderPane();
		//gp.setGridLinesVisible(true);
		
		
		
		//set the padding and gaps for the gp.
		gp.setPadding(new Insets(10));
		gp.setHgap(10);
		gp.setVgap(10);
		
		bp.setTop(mBar);
		bp.setCenter(gp);
		
		
		//set alignment.
		gp.setAlignment(Pos.CENTER);
		
		//Add component layout.
		gp.add(lblAvailableTrack, 0, 0);
		gp.add(lblPlaylist, 2, 0);
		gp.add(lblVolume, 1, 7);
		gp.add(lblMusicTime, 2, 10);
		
		gp.setMargin(musicVolume, new Insets(-10, 0, 0, 0)); //set volume slider and label margin.
		

		//Adding Buttons.
		gp.add(btnAddMusic, 1, 1);
		gp.add(btnRemoveMusic, 1, 2);
		gp.add(btnRemoveAllMusic, 1, 3);
		gp.add(btnPlayMusic, 1, 4);
		gp.add(btnPauseMusic, 1, 5);
		gp.add(btnStopMusic, 1, 6);
		
		
		//Adding ListView.
		gp.add(lstAvailableTrack, 0, 1, 1, 9);
		gp.add(lstPlaylist, 2, 1, 1, 9);
		
	
		//Add slider.
		gp.add(musicSlider, 2, 11);
		gp.add(musicVolume, 1, 8);
		
		
		
		
		//create scene.
		Scene s = new Scene(bp);
		
		//Stylesheet.
		s.getStylesheets().add(getClass().getResource("design.css").toExternalForm());
		
		//set the scene
		pStage.setScene(s);
		
		//show stage
		pStage.show();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Launch the application
		launch();

	}

}
