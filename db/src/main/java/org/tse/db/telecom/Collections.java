package org.tse.db.telecom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

import org.tse.db.types.*;

public class Collections implements Serializable {

	private static final long serialVersionUID = 2053443173473217147L;
	
	private HashMap<String, Series> collection;

	public Collections() {
		this.collection = new HashMap<String, Series>();
	}

	public HashMap<String, Series> getCollection() {
		return collection;
	}

	public void setCollection(HashMap<String, Series> collection) {
		this.collection = collection;
	}

	public void addSerie(String nom, Series serie) {
		this.collection.put(nom, serie);
	}

	public Series getSerie(String nom) {
		return this.collection.get(nom);
	}

	public Set<String> getKeys() {
		return this.collection.keySet();
	}

	public void deltaCompression() {
		this.collection.forEach((titre, serie) -> serie.deltaCompression());
	}

	public void deltaDecompression() {
		this.collection.forEach((titre, serie) -> serie.deltaDecompression());
	}

	@Override
	public String toString() {
		return "Collections [collection=" + collection + "]";
	}
	
	public void LoadData(String filename) {
		File fichiers =  new File(filename) ;

		 // ouverture d'un flux sur un fichier
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(fichiers));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
				
		 // désérialization de l'objet
		Collections m = null;
		try {
			m = (Collections)ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(m) ;
		this.collection=m.collection;
		
	}
	
	public void SaveData(String filename) {
		File fichier =  new File(filename) ;


		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(fichier));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		


		try {
			oos.writeObject(this) ;
		} catch (IOException e) {

			e.printStackTrace();
		}

		
		
		
		
		
	}

}
