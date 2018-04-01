package test;

import static org.junit.Assert.*;

import org.junit.Test;

import poutine.Facture;

public class TestFacture {

	private Facture testFacture;

	@Test
	public void testAucuneFacture() {
		System.out.println();
		testFacture = new Facture();
		testFacture.affichageFacture();
		assertNull( testFacture.ligneFac );
	}

	@Test
	public void testFactureFonctionnel() {
		System.out.println();
		testFacture = new Facture();
		testFacture.addListeClients( "Adrien" );
		testFacture.addListePlats( "Frites 2.5" );
		testFacture.addListeCommandes( "Adrien Frites 3" );
		testFacture.affichageFacture();
		assertEquals( testFacture.ligneFac, "Adrien 7,5$, TPS: 0,38$ TVQ: 0,75$ Total: 8,62$" );
	}
	
	@Test
	public void testFactureNonFonctionnelMauvaisClient() {
		System.out.println();
		testFacture = new Facture();
		testFacture.addListeClients( "Adrien" );
		testFacture.addListePlats( "Frites 2.5" );
		testFacture.addListeCommandes( "Roger Frites 3" );
		testFacture.affichageFacture();
		assertNull(testFacture.ligneFac);
	}
	
	@Test
	public void testFactureNonFonctionnelMauvaisPlat() {
		System.out.println();
		testFacture = new Facture();
		testFacture.addListeClients( "Adrien" );
		testFacture.addListePlats( "Frites 2.5" );
		testFacture.addListeCommandes( "Adrien Poulet 3" );
		testFacture.affichageFacture();
		assertNull(testFacture.ligneFac);
	}
	
	@Test
	public void testFactureNonFonctionnelMauvaiseQuantite() {
		System.out.println();
		testFacture = new Facture();
		testFacture.addListeClients( "Adrien" );
		testFacture.addListePlats( "Frites 2.5" );
		testFacture.addListeCommandes( "Adrien Frites 0" );
		testFacture.affichageFacture();
		assertNull(testFacture.ligneFac);
	}
}
