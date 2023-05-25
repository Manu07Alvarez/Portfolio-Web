import { PortfolioService } from '../../service/portfolio.service';
import { Component, OnInit }  from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  miPortfolio:any;
  constructor(private datosPortfolio:PortfolioService) {}

  ngOnInit() {
    this.datosPortfolio.obtenerDatos().subscribe(data =>{
      console.log(data);
      this.miPortfolio=data;
    });
  }

}

