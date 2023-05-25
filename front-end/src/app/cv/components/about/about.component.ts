import { Component, OnInit } from '@angular/core';
import { PortfolioService } from '../../service/portfolio.service';
@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.scss']
})
export class AboutComponent implements OnInit {
  miAbout:any;
  constructor(private datosPortfolio:PortfolioService) {}

  ngOnInit() {
    this.datosPortfolio.obtenerDatos().subscribe(data =>{
      console.log(data);
      this.miAbout=data;
    });
  }
}
