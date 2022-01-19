package org.tse.db.utils;

import java.util.Arrays;
import java.util.Collection;

import org.tse.db.types.*;
import org.tse.db.telecom.Collections;

public class Init {

	public static Collections initialize() {
		Collections collec = new Collections();
		
		Collection<Data> toto = Arrays.asList(new Data(new Timestamp((long) 1), 40.0), new Data(new Timestamp((long) 2), 15.0), new Data(new Timestamp((long) 3), 2024.0));
		collec.addSerie("Toto", new Series(toto));
		
		Collection<Data> greg = Arrays.asList(new Data(new Timestamp((long) 89), 1.0), new Data(new Timestamp((long) 90), -8.9), new Data(new Timestamp((long) 91), 46.2));
		collec.addSerie("Greg", new Series(greg));
		
		return collec;
	}
}
