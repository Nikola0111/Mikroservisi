import {FuelType} from '../enums/fuelType';
import {TransmissionType} from '../enums/transmissionType';
import {CarClass} from '../enums/carClass';
import { User } from './user';

export class AdvertisementInCart {
  id: number;
  name: string;
  model: string;
  brand: string;
  fuelType: FuelType;
  transType: TransmissionType;
  carClass: CarClass;
  travelled: number;
  price: number;
  carSeats: number;
  postedBy: User;
  owner: boolean;
  together:boolean;
  

  constructor() {

  }


}
