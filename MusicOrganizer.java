import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

/**
 * A class to hold details of audio tracks.
 * Individual tracks may be played.
 * Multiple tracks may also be played at your own risk
 * 
 * @author Claire Iroudayassamy
 * @version 2019.02.20
 */
public class MusicOrganizer
{
    // An ArrayList for storing music tracks.
    private ArrayList<Track> tracks;
    // A player for the music tracks.
    private MusicPlayer player;
    // A reader that can read music files and load them as tracks.
    private TrackReader reader;
    //A Random object.
    private Random random;
    //An integer that will eventually hold a random int value.
    private int randomInt;

    /**
     * Create a MusicOrganizer
     */
    public MusicOrganizer()
    {
        tracks = new ArrayList<>();
        player = new MusicPlayer();
        reader = new TrackReader();
        random = new Random();
        readLibrary("../audio");
        System.out.println("Music library loaded. " + getNumberOfTracks() + " tracks.");
        System.out.println();
    }
    
    /**
     * Add a track file to the collection.
     * @param filename The file name of the track to be added.
     */
    public void addFile(String filename)
    {
        tracks.add(new Track(filename));
    }
    
    /**
     * Add a track to the collection.
     * @param track The track to be added.
     */
    public void addTrack(Track track)
    {
        tracks.add(track);
    }
    
    /**
     * Play a track in the collection.
     * @param index The index of the track to be played.
     */
    public void playTrack(int index)
    {
        if(indexValid(index)) {
            Track track = tracks.get(index);
            player.startPlaying(track.getFilename());
            System.out.println("Now playing: " + track.getArtist() + " - " + track.getTitle());
        }
    }
    
    /**
     * Play a random track in the colletion
     */
    public void playRandomTrack()
    {
        if(tracks.size() > 0) {
            randomInt = random.nextInt(tracks.size()); //random index number in tracks
            Track track = tracks.get(randomInt);
            player.startPlaying(track.getFilename());
            System.out.println("Now playing: " + track.getArtist() + " - " + track.getTitle());
        }
    }
    
    /**
     * Play a sample of all tracks in the collection in random order.
     * Each track is only played once.
     */
    public void shuffleAllTracks()
    {
        if(tracks.size() > 0) {
            ArrayList<Track> shufflePlay = new ArrayList<Track>(tracks); //creates a copy of the tracks arraylist
            Collections.shuffle(shufflePlay); //shuffles the shufflePlay list.
            
            System.out.println("\n>>>>>>CRITICAL PAUSE BUTTON FAILURE!<<<<<<" +
                                "\n>>PLEASE RESET JVR TO STOP PLAYING MUSIC<<\n");
            
            for(Track track : shufflePlay) {
                System.out.println("Now playing: " + track.getArtist() + " - " + track.getTitle());
                player.playSample(track.getFilename());
            }
            
            System.out.println("\n>>>>PAUSE BUTTON BACK ONLINE<<<<");
        }
    }
    
    /**
     * Plays all tracks in the list in random order...at the same time.
     * Not for the faint of heart.
     */
    public void NiGhTMaReMoDe()
    {
        if(tracks.size() > 0) {
            ArrayList<Track> shufflePlay = new ArrayList<Track>(tracks); //creates a copy of the tracks arrayList
            
            while(shufflePlay.size() > 0) {
                randomInt = random.nextInt(shufflePlay.size());
                Track track = shufflePlay.get(randomInt);
                player.startPlaying(track.getFilename());
                shufflePlay.remove(randomInt);
            }
            
            System.out.println("\nNow playing : ERROR\n");
            System.out.println(">>>>>>>>>I HEARD YOU LIKE SONGS<<<<<<<<<" +
                               "\n>>>>SO I PUT SONGS INSIDE YOUR SONGS<<<<" +
                               "\n>>>>>>>>>>>>>>>>>>ENJOY<<<<<<<<<<<<<<<<<\n");
            System.out.println("Reset JVR at anytime to exit Nightmare Mode\n");
        }
    }
    
    /**
     * Return the number of tracks in the collection.
     * @return The number of tracks in the collection.
     */
    public int getNumberOfTracks()
    {
        return tracks.size();
    }
    
    /**
     * List a track from the collection.
     * @param index The index of the track to be listed.
     */
    public void listTrack(int index)
    {
        System.out.print("Track " + index + ": ");
        Track track = tracks.get(index);
        System.out.println(track.getDetails());
    }
    
    /**
     * Show a list of all the tracks in the collection.
     */
    public void listAllTracks()
    {
        System.out.println("Track listing: ");

        for(Track track : tracks) {
            System.out.println(track.getDetails());
        }
        System.out.println();
    }
    
    /**
     * List all tracks by the given artist.
     * @param artist The artist's name.
     */
    public void listByArtist(String artist)
    {
        for(Track track : tracks) {
            if(track.getArtist().contains(artist)) {
                System.out.println(track.getDetails());
            }
        }
    }
    
    /**
     * Remove a track from the collection.
     * @param index The index of the track to be removed.
     */
    public void removeTrack(int index)
    {
        if(indexValid(index)) {
            tracks.remove(index);
        }
    }
    
    /**
     * Play the first track in the collection, if there is one.
     */
    public void playFirst()
    {
        if(tracks.size() > 0) {
            player.startPlaying(tracks.get(0).getFilename());
        }
    }
    
    /**
     * Stop the player.
     */
    public void stopPlaying()
    {
        player.stop();
    }

    /**
     * Determine whether the given index is valid for the collection.
     * Print an error message if it is not.
     * @param index The index to be checked.
     * @return true if the index is valid, false otherwise.
     */
    private boolean indexValid(int index)
    {
        // The return value.
        // Set according to whether the index is valid or not.
        boolean valid;
        
        if(index < 0) {
            System.out.println("Index cannot be negative: " + index);
            valid = false;
        }
        else if(index >= tracks.size()) {
            System.out.println("Index is too large: " + index);
            valid = false;
        }
        else {
            valid = true;
        }
        return valid;
    }
    
    private void readLibrary(String folderName)
    {
        ArrayList<Track> tempTracks = reader.readTracks(folderName, ".mp3");

        // Put all thetracks into the organizer.
        for(Track track : tempTracks) {
            addTrack(track);
        }
    }
    
    public void playSample(int index)
    {
        Track track = tracks.get(index);
        player.playSample(track.getFilename());
    }
}
