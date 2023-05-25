import { Component, OnInit } from '@angular/core';
import { PortfolioService } from '../../service/portfolio.service';

@Component({
  selector: 'app-experience',
  templateUrl: './experience.component.html',
  styleUrls: ['./experience.component.scss']
})
export class ExperienceComponent {
  experienceList:any;
  constructor(private datosPortfolio:PortfolioService) {}

  ngOnInit() {
    this.datosPortfolio.obtenerDatos().subscribe(data =>{
      console.log(data);
      this.experienceList=data.experience;
    });
  }
}
