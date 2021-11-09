import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PersonRefererBillingComponent } from './list/person-referer-billing.component';
import { PersonRefererBillingDetailComponent } from './detail/person-referer-billing-detail.component';
import { PersonRefererBillingUpdateComponent } from './update/person-referer-billing-update.component';
import { PersonRefererBillingDeleteDialogComponent } from './delete/person-referer-billing-delete-dialog.component';
import { PersonRefererBillingRoutingModule } from './route/person-referer-billing-routing.module';

@NgModule({
  imports: [SharedModule, PersonRefererBillingRoutingModule],
  declarations: [
    PersonRefererBillingComponent,
    PersonRefererBillingDetailComponent,
    PersonRefererBillingUpdateComponent,
    PersonRefererBillingDeleteDialogComponent,
  ],
  entryComponents: [PersonRefererBillingDeleteDialogComponent],
})
export class PersonRefererBillingModule {}
