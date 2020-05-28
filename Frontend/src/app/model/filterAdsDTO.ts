import {FuelType} from '../enums/fuelType';
import {TransmissionType} from '../enums/transmissionType';
import {CarClass} from '../enums/carClass';

export class FilterAdsDTO {
  fuelType: FuelType;
  transmissionType: TransmissionType;
  carClass: CarClass;
  travelledFrom: number;
  travelledTo: number;
  priceFrom: number;
  priceTo: number;
  carSeats: number;
  timeFrom: Date;
  timeTo: Date;
  
  

  constructor() {

  }


}
