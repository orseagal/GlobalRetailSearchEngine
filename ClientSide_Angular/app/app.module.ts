import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { AppRoutingModule } from './app-routing.module';
import { AboutUsComponent } from './about-us/about-us.component';
import { LoginComponent } from './auth/login.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { SignUpComponent } from './auth/sign-up/sign-up.component';
import { DropDownDirective } from './shared/drop-down.directive';
import { LoadingSpinnerComponent } from './shared/loading-spinner/loading-spinner.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ContactComponent } from './contact/contact.component';
import { AdminComponent } from './admin/admin.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import {MatExpansionModule} from '@angular/material/expansion';
import { ModalModule } from 'ngx-bootstrap/modal';
import { ConfirmDialogComponent } from './shared/confirm-dialog/confirm-dialog.component';
import { EditCustomersComponent } from './admin/customers/edit-customers/edit-customers.component';
import { CreateCustomersComponent } from './admin/customers/create-customers/create-customers.component';
import { CustomersListComponent } from './admin/customers/customers-list/customers-list.component';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatFormFieldModule} from '@angular/material/form-field';
import { NotificationDialogComponent } from './shared/notification-dialog/notification-dialog.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { DatepickerPopupComponent } from './shared/datepicker-popup/datepicker-popup.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {A11yModule} from '@angular/cdk/a11y';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {PortalModule} from '@angular/cdk/portal';
import {ScrollingModule} from '@angular/cdk/scrolling';
import {CdkStepperModule} from '@angular/cdk/stepper';
import {CdkTableModule} from '@angular/cdk/table';
import {CdkTreeModule} from '@angular/cdk/tree';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatBadgeModule} from '@angular/material/badge';
import {MatBottomSheetModule} from '@angular/material/bottom-sheet';

import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {MatCardModule} from '@angular/material/card';

import {MatChipsModule} from '@angular/material/chips';
import {MatStepperModule} from '@angular/material/stepper';

import {MatDialogModule} from '@angular/material/dialog';
import {MatDividerModule} from '@angular/material/divider';

import {MatInputModule} from '@angular/material/input';

import {MatMenuModule} from '@angular/material/menu';
import {MatNativeDateModule, MatRippleModule} from '@angular/material/core';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatRadioModule} from '@angular/material/radio';
import {MatSelectModule} from '@angular/material/select';

import {MatSliderModule} from '@angular/material/slider';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatSortModule} from '@angular/material/sort';
import {MatTableModule} from '@angular/material/table';
import {MatTabsModule} from '@angular/material/tabs';
//import { Ng2SearchPipeModule } from 'ng2-search-filter';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatTreeModule} from '@angular/material/tree';
import {environment} from "../environments/environment";
import { DialogOverviewDialog } from './shared/mat-dialog/mat-dialog';
import {MatGridListModule} from '@angular/material/grid-list';
import { MatConfirmDialogComponent } from './shared/mat-confirm-dialog/mat-confirm-dialog.component';
import { BasicAuthHttpInterceptorService } from './shared/basic-auth-http-interceptor.service';
import { AboutUsSlideComponent } from './about-us/about-us-slide/about-us-slide.component';
import { SearchesComponent } from './searches/searches.component';
import { EbayComponent } from './ebay/ebay.component';
import { MercadolibreComponent } from './mercadolibre/mercadolibre.component';
import {NgxPaginationModule} from 'ngx-pagination';
import { KijijiComponent } from './kijiji/kijiji.component';
import { UserPasswordChangeComponent } from './user/password-change/user-password-change.component';
import { UserProfileComponent } from './user/profile/user-profile.component';
import { UserComponent } from './user/user.component';
import { ToastrModule } from 'ngx-toastr';
import {NgxWebstorageModule} from 'ngx-webstorage';
import { TokenInterceptor } from './shared/token-Interceptor';
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    AboutUsComponent,
    LoginComponent,
    PageNotFoundComponent,
    SignUpComponent,
    DropDownDirective,
    LoadingSpinnerComponent,
    ContactComponent,
    AdminComponent,
    ConfirmDialogComponent,
    EditCustomersComponent,
    CreateCustomersComponent,
    CustomersListComponent,
    UserComponent,
    UserProfileComponent,
    UserPasswordChangeComponent,
    NotificationDialogComponent,
    DatepickerPopupComponent,
    DialogOverviewDialog,
    MatConfirmDialogComponent,
    AboutUsSlideComponent,
    SearchesComponent,
    EbayComponent,
    MercadolibreComponent,
    KijijiComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AppRoutingModule,
    FormsModule,
    MatCheckboxModule,
    ReactiveFormsModule,
    HttpClientModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatExpansionModule,
    MatFormFieldModule,
    ModalModule.forRoot(),
    NgbModule,
    MatDatepickerModule,
    A11yModule,
    CdkStepperModule,
    CdkTableModule,
    CdkTreeModule,
    DragDropModule,
    MatAutocompleteModule,
    MatBadgeModule,
    MatBottomSheetModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatStepperModule,
    MatDatepickerModule,
    MatDialogModule,
    MatDividerModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    MatTreeModule,
    PortalModule,
    ScrollingModule,
    NgxPaginationModule,
   // Ng2SearchPipeModule,
    NgxWebstorageModule.forRoot(),
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
  ],
  providers: [ {
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
