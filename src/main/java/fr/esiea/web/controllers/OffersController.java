package fr.esiea.web.controllers;

import fr.esiea.model.offers.Offer;
import fr.esiea.web.SupermarketService;
import fr.esiea.web.exceptions.OfferIndexNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequestMapping("/supermarket/offers/")
@RestController
public class OffersController {

	public SupermarketService service = SupermarketService.SERVICE;

	@GetMapping(value="active", produces = "application/json")
	public List<Offer> getActiveOffers(){
		return service.getActiveOffers("");
	}

	@GetMapping(value="active/simple", produces = "application/json")
	public List<Offer> getSimpleActiveOffers(){
		return service.getActiveOffers("simple");
	}

	@GetMapping(value="active/bundle", produces = "application/json")
	public List<Offer> getBundleActiveOffers(){
		return service.getActiveOffers("bundle");
	}


	@GetMapping(value="inactive", produces = "application/json")
	public List<Offer> getInactiveOffers(){
		return service.getInactiveOffers();
	}

	@GetMapping(value="activate/{index}", produces = "application/json")
	public Offer activateOffer(@PathVariable int index){
		Offer o = service.activateOffer(index);
		if(o!= null){
			return o;
		} else{
			throw new OfferIndexNotFoundException();
		}
	}

	@GetMapping(value="deactivate/{index}", produces = "application/json")
	public Offer deactivateOffer(@PathVariable int index){
		Offer o = service.deactivateOffer(index);
		if(o!= null){
			return o;
		} else{
			throw new OfferIndexNotFoundException();
		}
	}

}
