import { MatSliderModule } from '@angular/material/slider';
import { MatSidenavModule } from '@angular/material/sidenav';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { BrowserAnimationsModule, NoopAnimationsModule } from '@angular/platform-browser/animations';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import { MainComponent } from './main/main.component';

import { ViewComponent } from './view/view.component';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatAutocompleteModule} from '@angular/material/autocomplete'
import {FormsModule, ReactiveFormsModule} from '@angular/forms';;
import { MatSelectModule } from '@angular/material/select';
import {MatPaginatorModule} from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { SearchComponent } from './search/search.component';
import { ProfileComponent } from './profile/profile.component';
import { MakerComponent } from './maker/maker.component'
import { HttpClientModule } from '@angular/common/http';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
//import { NgxDocViewerModule } from 'ngx-doc-viewer';

@NgModule({

  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    MainComponent,

    ViewComponent,
    SearchComponent,
    ProfileComponent,
    MakerComponent,

  ],
  imports: [
   // NgxDocViewerModule,
    FormsModule,
    ReactiveFormsModule,
    MatAutocompleteModule,
    MatSidenavModule,
    MatSliderModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    AppRoutingModule,
    AppRoutingModule,
    NoopAnimationsModule,
    MatPaginatorModule,
    MatTableModule,
    MatCardModule,
    HttpClientModule,
    MatButtonModule,
    MatSelectModule,
    MatCheckboxModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
