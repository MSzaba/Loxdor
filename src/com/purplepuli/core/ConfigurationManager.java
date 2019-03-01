package com.purplepuli.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.purplepuli.logging.LogManager;
import com.purplepuli.logging.Logger;

public class ConfigurationManager {
	private static final String INI_FILENAME = "config.ini";
	private PPContext context;
	private static ConfigurationManager instance;
	private Logger logger;
	

	private ConfigurationManager(PPContext context) throws PPException {
		this.context = context;
		//
	}
	
	public static ConfigurationManager getInstance(PPContext context) throws PPException  {
		if (instance == null) {
			instance = new ConfigurationManager(context);
		}
		return instance;
	}

	public void initialLoad() throws PPException {
		String instanceHome = (String) context.get(PPContext.INSTANCE_HOME);
		File iniFile = new File(instanceHome + File.separator + INI_FILENAME);
		Map<String, Object> parameters = loadParametersFromFile(iniFile);
		
		logger = LogManager.getInstanceWithParameters(parameters).getLogger(this);
	}

	private Map<String, Object> loadParametersFromFile(File iniFile) throws PPException {
		 //reader = null;
		Map<String, Object> list = new HashMap<>();
		int lineNumber = 0;
		try(BufferedReader reader = new BufferedReader(new FileReader(iniFile))) {
		    
		    String text = null;

		    while ((text = reader.readLine()) != null) {
		        
		        if (!text.contains("=")) {
		        	throw new PPException("Line " + lineNumber + " does not contain '=' character!", "Line: " + text);
				}
		        //TODO continue from here
		    }
		} catch (FileNotFoundException e) {
			throw new PPException(e.getMessage(), "File is missing: " + iniFile.getName());
		   
		} catch (IOException e) {
			throw new PPException(e.getMessage(), "Unable to open file: " + iniFile.getName());
		} 
		return list;
	}

}
