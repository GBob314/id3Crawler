/*
 * CREDIT FOR MP3 ID3 LIBRARY BELONGS TO http://www.beaglebuddy.com/
 *
 *
 * Copyright 2015 Robert Gonser

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 *
 *
 *
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;
import com.beaglebuddy.mp3.MP3;

public class id3Crawler {

	public static MP3 mp3;
	public static int songCounter = 0;
	public static long startTime = 0;

	public static void main(String[] args) throws IOException {

		//Input for the directory to be searched.
		System.out.println("Please enter a directory: ");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		
		//Start a timer to calculate runtime
		startTime = System.currentTimeMillis();
		
		System.out.println("Starting scan...");

		//Files for output
		PrintWriter pw1 = new PrintWriter(new FileWriter("C:/Users/gbob3_000/Desktop/id3CrawlerOutput/Song.txt"));
		PrintWriter pw2 = new PrintWriter(new FileWriter("C:/Users/gbob3_000/Desktop/id3CrawlerOutput/Artist.txt"));
		PrintWriter pw3 = new PrintWriter(new FileWriter("C:/Users/gbob3_000/Desktop/id3CrawlerOutput/Album.txt"));
		PrintWriter pw4 = new PrintWriter(new FileWriter("C:/Users/gbob3_000/Desktop/id3CrawlerOutput/PerformedBy.txt"));
		PrintWriter pw5 = new PrintWriter(new FileWriter("C:/Users/gbob3_000/Desktop/id3CrawlerOutput/TrackOf.txt"));
		PrintWriter pw6 = new PrintWriter(new FileWriter("C:/Users/gbob3_000/Desktop/id3CrawlerOutput/CreatedBy.txt"));

		//This is used for creating IDs for artists, songs, albums.
		int idCounter = 0;

		//This is used to prevent duplicate artists
		String previousArtist = " ";
		String currentArtist;
		int artistID = 0;

		//This is used to prevent duplicate albums
		String previousAlbum = " ";
		String currentAlbum;
		int albumID = 0;

		//This array holds valid extensions to iterate through
		String[] extensions = new String[]{"mp3"};

		//iterate through all files in a directory
		Iterator<File> it = FileUtils.iterateFiles(new File(input), extensions, true);
		while(it.hasNext()){

			//open the next file
			File file = it.next();

			//instantiate an mp3file object with the opened file
			MP3 song = GetMP3(file);

			//pass the song through SongInfo and return the required information
			SongInfo info = new SongInfo(song);


			//This is used to prevent duplicate artists/albums
			currentArtist = info.getArtistInfo();
			currentAlbum = info.getAlbumInfo();

			//Append the song information to the end of a text file
			pw1.println(idCounter + "\t" + info.getTitleInfo());

			//This prevents duplicates of artists
			if(!(currentArtist.equals(previousArtist))){
				pw2.println(idCounter + "\t" + info.getArtistInfo());
				previousArtist = currentArtist;
				artistID = idCounter;
			}

			//This prevents duplicates of albums
			if(!(currentAlbum.equals(previousAlbum))){
				pw3.println(idCounter + "\t" + info.getAlbumInfo());
				previousAlbum = currentAlbum;
				albumID = idCounter;

				//This formats the IDs for a "CreatedBy" relationship table
				pw6.println(artistID + "\t" + albumID);
			}

			//This formats the IDs for a "PerformedBy" relationship table
			pw4.println(idCounter + "\t" + artistID);

			//This formats the IDs for a "TrackOf" relationship table
			pw5.println(idCounter + "\t" + albumID);

			idCounter++;
			songCounter++;

		}
		scanner.close();
		pw1.close();
		pw2.close();
		pw3.close();
		pw4.close();
		pw5.close();
		pw6.close();
		
		System.out.println("Scan took " + ((System.currentTimeMillis() - startTime)/1000.0)
				+ " seconds to scan " + songCounter + " items!");
		
	}

	public static MP3 GetMP3(File input){

		try
		{
			mp3 = new MP3(input);

			// if there was any invalid information (ie, ID3v2.x frames) in the .mp3 file,
			// then display the errors to the user
			if (mp3.hasErrors())
			{
				//mp3.displayErrors(System.out);      // display the errors that were found
			}
		}
		catch (IOException ex)
		{
			System.out.println("An error occurred while reading the mp3 file: " + mp3.getTitle());
			//ex.printStackTrace();
		}

		return mp3;

	}

}
