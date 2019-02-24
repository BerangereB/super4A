package fr.esiea;

import fr.esiea.model.market.Product;
import fr.esiea.model.market.ProductUnit;
import fr.esiea.model.offers.BundleOfferFactory;
import fr.esiea.model.offers.Offer;
import fr.esiea.model.offers.OfferType;
import fr.esiea.model.offers.SimpleOfferFactory;
import fr.esiea.web.OffersController;
import fr.esiea.web.SupermarketService;
import fr.esiea.web.exceptions.OfferIndexNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OffersControllerTest {

	private Offer offer1;
	private Offer offer2;
	private Offer offer3;

	@Autowired
	private OffersController controller;

	@Before
	public void initOffers(){
		Product toothbrush = new Product("toothbrush", ProductUnit.Each,0.99);
		Product toothpaste = new Product("Toothpaste", ProductUnit.Each,0.89);
		Map<String,Integer> productsBundle = new HashMap<String, Integer>();
		productsBundle.put(toothbrush.getName(),2);
		productsBundle.put(toothpaste.getName(),2);

		offer1 = SimpleOfferFactory.getOffer(OfferType.ThreeForTwo, toothbrush.getName(), 0.0);
		offer2 = SimpleOfferFactory.getOffer(OfferType.FiveForAmount, toothpaste.getName(), 2.5);
		offer3 = BundleOfferFactory.getOffer(OfferType.PercentBundle, productsBundle, 20);

	}

	@Before
	public void resetTeller(){
		SupermarketService.reset();
	}

	@Test
	public void testDisplayAllOffers(){
		List<Offer> expected = Arrays.asList(offer1,offer2,offer3);

		List<Offer> result = controller.getActiveOffers();

		assertTrue(expected.size() == result.size() && expected.containsAll(result));
	}

	@Test
	public void testDisplayInactiveOffersAfterDeactivateOne(){

		controller.deactivateOffer(0);
		List<Offer> inactiveOffers = controller.getInactiveOffers();

		assertTrue(1 == inactiveOffers.size() && inactiveOffers.contains(offer1));
	}


	@Test
	public void testDisplaySimpleOffers(){

		List<Offer> expected = Arrays.asList(offer1,offer2);

		List<Offer> result = controller.getSimpleActiveOffers();

		assertTrue(expected.size() == result.size() && expected.containsAll(result));
	}


	@Test
	public void testDisplayBundleOffers(){

		List<Offer> expected = new ArrayList<Offer>();
		expected.add(offer3);

		List<Offer> result = controller.getBundleActiveOffers();

		assertTrue(expected.size() == result.size() && expected.containsAll(result));
	}


	@Test
	public void testDisplayOffersWithToothpaste(){

		List<Offer> expected = Arrays.asList(offer2,offer3);

		List<Offer> result = controller.getActiveOffersByProduct("toothpaste");

		assertTrue(expected.size() == result.size() && expected.containsAll(result));
	}



	@Test
	public void testDeactivateOffer_atIndex_0_inActiveOffers(){

		Offer res = controller.deactivateOffer(0);
		assertThat(offer1).isEqualTo(res);

		List<Offer> inactiveOffersRes = controller.getInactiveOffers();

		assertTrue(1 == inactiveOffersRes.size() && inactiveOffersRes.contains(offer1));

		List<Offer> activeOffersRes = controller.getActiveOffers();
		assertTrue(2 == activeOffersRes.size() && activeOffersRes.contains(offer2) && activeOffersRes.contains(offer3));
	}

	@Test(expected = OfferIndexNotFoundException.class)
	public void testDeactivateOffer_atIndex_3_inActiveOffers_return_exception(){
		controller.deactivateOffer(3);
	}



	@Test
	public void testActivateOffer_atIndex_0_inInactiveOffers(){
		controller.deactivateOffer(0);

		Offer res = controller.activateOffer(0);
		assertThat(offer1).isEqualTo(res);

		List<Offer> inactiveOffersRes = controller.getInactiveOffers();
		assertEquals(0, inactiveOffersRes.size());

		List<Offer> activeOffersRes = controller.getActiveOffers();
		assertTrue(3 == activeOffersRes.size()
			&& activeOffersRes.contains(offer1)
			&& activeOffersRes.contains(offer2)
			&& activeOffersRes.contains(offer3));
	}

	@Test(expected = OfferIndexNotFoundException.class)
	public void testActivateOffer_atIndex_0_inInactiveOffers_while_no_offer_inactive_return_exception(){
		controller.activateOffer(0);
	}




}

