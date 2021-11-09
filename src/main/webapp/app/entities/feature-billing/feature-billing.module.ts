import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FeatureBillingComponent } from './list/feature-billing.component';
import { FeatureBillingDetailComponent } from './detail/feature-billing-detail.component';
import { FeatureBillingUpdateComponent } from './update/feature-billing-update.component';
import { FeatureBillingDeleteDialogComponent } from './delete/feature-billing-delete-dialog.component';
import { FeatureBillingRoutingModule } from './route/feature-billing-routing.module';

@NgModule({
  imports: [SharedModule, FeatureBillingRoutingModule],
  declarations: [
    FeatureBillingComponent,
    FeatureBillingDetailComponent,
    FeatureBillingUpdateComponent,
    FeatureBillingDeleteDialogComponent,
  ],
  entryComponents: [FeatureBillingDeleteDialogComponent],
})
export class FeatureBillingModule {}
