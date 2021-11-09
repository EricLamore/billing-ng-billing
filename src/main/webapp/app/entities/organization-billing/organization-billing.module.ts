import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OrganizationBillingComponent } from './list/organization-billing.component';
import { OrganizationBillingDetailComponent } from './detail/organization-billing-detail.component';
import { OrganizationBillingUpdateComponent } from './update/organization-billing-update.component';
import { OrganizationBillingDeleteDialogComponent } from './delete/organization-billing-delete-dialog.component';
import { OrganizationBillingRoutingModule } from './route/organization-billing-routing.module';

@NgModule({
  imports: [SharedModule, OrganizationBillingRoutingModule],
  declarations: [
    OrganizationBillingComponent,
    OrganizationBillingDetailComponent,
    OrganizationBillingUpdateComponent,
    OrganizationBillingDeleteDialogComponent,
  ],
  entryComponents: [OrganizationBillingDeleteDialogComponent],
})
export class OrganizationBillingModule {}
