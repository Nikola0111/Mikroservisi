import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import { ShoppingCartDataSource, ShoppingCartItem } from './shopping-cart-datasource';
import { Advertisement } from 'src/app/model/advertisement';
import { ShopingCartService } from './shoping-cart.service';
import { AdvertisementInCart } from 'src/app/model/advertisementInCart';
import {SessionService} from '../../services/SessionService/session.service';

import { ItemInCart } from 'src/app/model/itemInCart';


@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements AfterViewInit, OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<ShoppingCartItem>;
  dataSource: ShoppingCartDataSource;

  advertisements: AdvertisementInCart[];
  dialogData: ItemInCart;
  selected: AdvertisementInCart;


  sameOwner: ItemInCart[];
  itemsInCart: ItemInCart[];

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['name', 'model', 'brand', 'fuelType', 'transType', 'carClass','owner', "checkbox", "button"];
//DODATI 'timeFrom' I 'timeTo'

  constructor(private shopingCartService: ShopingCartService, private sessionService: SessionService){
    console.log(this.sessionService.ulogovaniKorisnik);
  }


  ngOnInit() {
    this.dataSource = new ShoppingCartDataSource(null);
    this.shopingCartService.getAllForCart().subscribe(
      data => {
        this.itemsInCart = data;
        this.sameOwner= data;
        this.sameOwner.forEach(same => {
            this.itemsInCart.forEach(element => {
              
              if(same.id!=element.id){

              if(same.advertisement.postedBy.jmbg===element.advertisement.postedBy.jmbg){
                same.owner=true;
              }
            }
            });
        });

        this.dataSource = new ShoppingCartDataSource(this.sameOwner);
      }
    );
  }



  sendRequest(){
    console.log("Pogodio dugme u ts");
   
    this.dataSource = new ShoppingCartDataSource(null);
    this.shopingCartService.sentRequests(this.sameOwner).subscribe(
      data => {
    
        this.dataSource.data = data;
        this.table.dataSource = this.dataSource;
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
        console.log(this.dataSource.data);
        console.log(data);
      }

    );
  }

  remove(itemInCart: ItemInCart){
    console.log("pogodi ga");
    this.dataSource = new ShoppingCartDataSource(null);
    this.shopingCartService.removeFromCart(itemInCart).subscribe(
      data => {
    
        this.dataSource.data = data;
        this.table.dataSource = this.dataSource;
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
        console.log(this.dataSource.data);
        console.log(data);
      }

    );

 
  }


  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
  }
}
