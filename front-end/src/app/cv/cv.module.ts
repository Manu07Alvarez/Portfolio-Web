
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CvRoutingModule } from './cv-routing.module';
import { CvComponent } from './cv.component';
import { AboutComponent } from './components/about/about.component';
import { EducationComponent } from './components/education/education.component';
import { ExperienceComponent } from './components/experience/experience.component';
import { HeaderComponent } from './components/header/header.component';
import { ProjectsComponent } from './components/projects/projects.component';
import { SkillsComponent } from './components/skills/skills.component';


@NgModule({
  declarations: [
    CvComponent,
    AboutComponent,
    EducationComponent,
    ExperienceComponent,
    HeaderComponent,
    ProjectsComponent,
    SkillsComponent,
  ],
  imports: [
    CommonModule,
    CvRoutingModule
  ]
})
export class CvModule { }
