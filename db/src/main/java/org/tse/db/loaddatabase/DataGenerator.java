package org.tse.db.loaddatabase;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.tse.db.types.Data;
import org.tse.db.types.Series;
import org.tse.db.telecom.Collections;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

/* Cette classe g�n�re des donn�es afin de peupler la base
 * Les donn�es sont stock�es dans un fichier csv*/

public class DataGenerator {
	
	//Constructeur pour writer
	public DataGenerator(int n) throws IOException {
		
		List<String[]> data = createCsvDataSample(n);
		CSVWriter writer = null;
		try{
			String file = n+".csv";
			writer = new CSVWriter(new FileWriter(".\\data\\" + file));	
			writer.writeAll(data);			
		}catch (IOException e) {
			System.out.println("no");
		}finally {
			if (writer != null)
			writer.close();
		}
	}
	
	//Constructeur pour reader
	public DataGenerator() {
		
	}
	
	private static List<String[]> createCsvDataSample(int n) {
        
		// Generate data
		List<String[]> list = new ArrayList<String[]>();        
		Random rand = new Random();
        String str = null;
        Date d = null;
        String idCapt;
        String temp;
        String ts;
        
        Long startTs = Timestamp.valueOf("1970-01-01 00:00:00").getTime(); //1 janvier 1970 minuit
        Long endTs = Timestamp.valueOf("2025-01-01 00:00:00").getTime(); //1 lanvier 2025 minuit
        Long diff = endTs - startTs + 1;
        
        for (int i=0; i<n;i++){
        	//Id & value generated
        	idCapt = String.valueOf(rand.nextInt(64));		//G�n�re jusqu'� 64 id
        	temp = String.valueOf(rand.nextDouble()*35); 	//G�n�re jusqu'� 35�
        	//Timestamp
        	ts = new SimpleDateFormat("yyyyMMddhhmmss").format(new Timestamp(startTs + (long)(Math.random()*diff)));
        	//Parsing
        	str = idCapt +","+temp+","+ts;
        	list.add(str.split(","));	
		}
        return list;
    }
	
	// Fonction permettant la lecture et le remplissage de la base � partir des donn�es g�n�r�es ci-dessus
	public void ReadData (Collections collec,String path) {
		//Cr�ation d'une collection
		//Collections collec = new Collections();
		String PATH = ".\\data\\" + path + ".csv";
		//Remplissage de la collection
		try {
            Reader reader = Files.newBufferedReader(Paths.get(PATH));
            CSVReader csvReader = new CSVReader(reader);      
			String [] buffer;
			
			while((buffer = csvReader.readNext()) != null) {				
				//[0] : id, [1] : value, [2] : ts
				Data data = new Data(buffer[2],buffer[1]);
				if(collec.getSerie(buffer[0]) != null)
				{
					//Nous ajoutons un membre � la s�rie correspondante
					collec.getSerie(buffer[0]).addData(data);
				}else {
					//Nous ajoutons une nouvelle s�rie
					Collection<Data> d = Arrays.asList(data);
					collec.addSerie(buffer[0], new Series(d));					
				}								
			}
		}catch(IOException e) {
			e.printStackTrace();
		} catch (CsvValidationException e) {
			e.printStackTrace();
		}
	}
}
