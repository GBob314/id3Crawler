import com.beaglebuddy.mp3.MP3;

public class SongInfo {
	
	private MP3 mp3;
	
	public SongInfo(MP3 mp3file){
			mp3 = mp3file;
	}

	//Returns "Title	Artist	Album"
	public String getAllInfo(){
		return(
				mp3.getTitle()
       		 + "\t" + mp3.getBand()
       		 + "\t" + mp3.getAlbum());
	}
	
	public String getArtistInfo(){
		return(mp3.getBand());
	}
	
	public String getTitleInfo(){
		return(mp3.getTitle());
	}
	
	public String getAlbumInfo(){
		return(mp3.getAlbum());
	}
}
