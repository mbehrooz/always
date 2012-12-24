package edu.wpi.disco.rt.actions;

import java.util.*;

import org.w3c.dom.*;


import edu.wpi.always.*;
import edu.wpi.always.test.*;
import edu.wpi.cetask.*;
import edu.wpi.disco.*;
import edu.wpi.disco.rt.*;

public class LoadModelFromDocument implements DiscoFunc<TaskModel> {

	private final Document doc;
	private final Properties properties;
	private final Properties translateProperties;

	public LoadModelFromDocument(Document doc, Properties properties,
			Properties translateProperties) {
		this.doc = doc;
		this.properties = properties;
		this.translateProperties = translateProperties;
	}

	public LoadModelFromDocument(DiscoDocumentSet docSet) {
		this(docSet.getMainDocument(), docSet.getProperties(), docSet
				.getTranslateProperties());
	}

	@Override
	public TaskModel execute(Disco disco) {
		return disco.load(null, doc, properties, translateProperties);
	}

}