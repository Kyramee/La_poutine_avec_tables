package poutine;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Facture {

	private ArrayList<String> listeClients;
	private ArrayList<String> listeNomPlats;
	private ArrayList<String> listePrixPlats;
	private ArrayList<String> mapKeyTable;
	private HashMap<String, ArrayList<String>> clientsKey;
	private HashMap<String, HashMap<String, Double>> listeTable;
	private BufferedWriter ficEcriture;

	public String ligneFac; // Ligne utile uniquement pour tester

	// Constructeur
	public Facture() {
		this.listeClients = new ArrayList<>();
		this.listeNomPlats = new ArrayList<>();
		this.listePrixPlats = new ArrayList<>();
		this.mapKeyTable = new ArrayList<>();
		this.listeTable = new HashMap<>();
		this.clientsKey = new HashMap<>();
		ecrire();
		ecrire("Bienvenue chez Barette!");
	}

	public void addListeClients(String client) {
		this.listeClients.add(client);
	}

	public void addListePlats(String plat) {
		String[] platSplit = plat.split(" ");
		this.listeNomPlats.add(platSplit[0]);
		this.listePrixPlats.add(platSplit[1]);
	}

	public void addListeCommandes(String commande) {
		String[] comSplit = commande.split(" ");

		if (comSplit.length != 4) {
			ecrire("La commande " + commande + " ne possède pas un format de commande invalide");

		} else if (!this.listeClients.contains(comSplit[1])) {
			ecrire("Pour la commande " + commande + ", le nom du client n'est pas dans la liste");

		} else if (!this.listeNomPlats.contains(comSplit[2])) {
			ecrire("Pour la commande " + commande + ", le nom du plat n'est pas dans la liste");

		} else if (Integer.parseInt(comSplit[3]) < 1) {
			ecrire("Pour la commande " + commande
					+ ", la quantité de nourriture ne peux pas être négative ou égal à 0");
		} else {
			if (this.mapKeyTable.contains(comSplit[0])) {
				if(this.listeTable.get(comSplit[0]).containsKey(comSplit[1])) {
					this.listeTable.get(comSplit[0]).put(comSplit[1], this.listeTable.get(comSplit[0]).get(comSplit[1]) + calculerCoutPlat(comSplit));
				} else {
					this.clientsKey.get(comSplit[0]).add(comSplit[1]);
					this.listeTable.get(comSplit[0]).put(comSplit[1], calculerCoutPlat(comSplit));
				}
			} else {
				this.mapKeyTable.add(comSplit[0]);
				this.listeTable.put(comSplit[0], new HashMap<>());
				this.listeTable.get(comSplit[0]).put(comSplit[1], calculerCoutPlat(comSplit));
				this.clientsKey.put(comSplit[0], new ArrayList<>());
				this.clientsKey.get(comSplit[0]).add(comSplit[1]);
			}
		}
	}

	public void affichageFacture() {
		ArrayList<String> clients = new ArrayList<>();
		
		ecrire("\n");
		ecrire("FACTURE:");

		for (String table : this.mapKeyTable) {
			clients = this.clientsKey.get(table);
			ecrire("Table: " + table);

			for (String client : clients) {
				ecrire(this.listeTable.get(table).get(client), client);
			}
			ecrire("\n");
		}

		try {
			ficEcriture.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void ecrire() {
		String timeStamp = new SimpleDateFormat("dd_MM_yy-HH;mm").format(Calendar.getInstance().getTime());
		String filename = "Facture-du-" + timeStamp + ".txt";

		try {
			ficEcriture = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(filename), Charset.defaultCharset()));
		} catch (IOException err) {
			System.out.print(err);
		}
	}
	
	private void ecrire(String message) {
		System.out.println(message);

		try {
			ficEcriture.write(message);
			ficEcriture.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void ecrire(double prix, String nom) {
		double tps = prix * 0.05;
		double tvq = prix * 0.10;
		DecimalFormat df = new DecimalFormat("0.##");

		ecrire(nom + " " + df.format(prix) + "$, TPS: " + df.format(tps) + "$ TVQ: " + df.format(tvq)
		+ "$ Total: " + df.format((prix + tps + tvq)) + "$");
	}
		
	private double calculerCoutPlat(String[] comSplit) {
		return Double.parseDouble(this.listePrixPlats.get(this.listeNomPlats.indexOf(comSplit[2])))
				* Double.parseDouble(comSplit[3]);
	}
}