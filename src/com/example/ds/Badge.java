package com.example.ds;

import com.parse.*;;

@ParseClassName("Badge")
public class Badge extends ParseObject {
	public Badge(){
		
	}
	
	public ParseFile getPhotoFile() {
        return getParseFile("photo");
    }
	
    public void setPhotoFile(ParseFile file) {
        put("photo", file);
    }
    
    public void setEarner(ParseUser user) {
        put("earner", user);
    }
    
    public void setName(String name) {
        put("name", name);
    }
    
    public String getName() {
        return getString("name");
    }
	 

}
