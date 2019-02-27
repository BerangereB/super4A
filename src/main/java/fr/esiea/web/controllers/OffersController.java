package fr.esiea.web.controllers;

import fr.esiea.model.offers.Offer;
import fr.esiea.web.SupermarketService;
import fr.esiea.web.exceptions.OfferIndexNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<List<Offer>> getActiveOffers(){
		return new ResponseEntity<List<Offer>>(service.getActiveOffers(""),HttpStatus.OK);
	}

	@GetMapping(value="active/simple", produces = "application/json")
	public ResponseEntity<List<Offer>> getSimpleActiveOffers(){
		return new ResponseEntity<List<Offer>>(service.getActiveOffers("simple"), HttpStatus.OK);
	}

	@GetMapping(value="active/bundle", produces = "application/json")
	public ResponseEntity<List<Offer>> getBundleActiveOffers(){
		return new ResponseEntity<List<Offer>>(service.getActiveOffers("bundle"),HttpStatus.OK);
	}


	@GetMapping(value="inactive", produces = "application/json")
	public ResponseEntity<List<Offer>> getInactiveOffers(){
		return
			new ResponseEntity<List<Offer>>(service.getInactiveOffers(),HttpStatus.OK);
	}

	@GetMapping(value="activate/{index}", produces = "application/json")
	public ResponseEntity<Offer> activateOffer(@PathVariable int index){
		Offer o = service.activateOffer(index);
		if(o!= null){
			return new ResponseEntity<Offer>(o,HttpStatus.OK);
		} else{
			throw new OfferIndexNotFoundException();
		}
	}

	@GetMapping(value="deactivate/{index}", produces = "application/json")
	public ResponseEntity<Offer> deactivateOffer(@PathVariable int index){
		Offer o = service.deactivateOffer(index);
		if(o!= null){
			return new ResponseEntity<Offer>(o,HttpStatus.OK);
		} else{
			throw new OfferIndexNotFoundException();
		}
	}

}
