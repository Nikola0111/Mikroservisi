export class CarReport {
  public carId: number;
  public travelled: number;
  public comment: string;
  public bookingID: number;


  constructor(carId: number, traveled: number, comment: string, bookingID: number) {
    this.carId = carId;
    this.travelled = traveled;
    this.comment = comment;
    this.bookingID = bookingID;
  }
}
