import { Component, OnInit } from '@angular/core';
import { PortfolioService } from '../../service/portfolio.service';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.scss']
})
export class ProjectsComponent {
  projectsList:any;
  constructor(private datosPortfolio:PortfolioService) {}

  ngOnInit() {
    this.datosPortfolio.obtenerDatos().subscribe(data =>{
      console.log(data);
      this.projectsList=data.projects;
    });
  }
}
