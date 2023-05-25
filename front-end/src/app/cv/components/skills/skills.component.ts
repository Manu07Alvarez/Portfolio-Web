import { Component, OnInit } from '@angular/core';
import { PortfolioService } from '../../service/portfolio.service';

@Component({
  selector: 'app-skills',
  templateUrl: './skills.component.html',
  styleUrls: ['./skills.component.scss']
})
export class SkillsComponent {
  skillsList:any;
  constructor(private datosPortfolio:PortfolioService) {}

  ngOnInit() {
    this.datosPortfolio.obtenerDatos().subscribe(data =>{
      console.log(data);
      this.skillsList=data.skills;
    });
  }
}
