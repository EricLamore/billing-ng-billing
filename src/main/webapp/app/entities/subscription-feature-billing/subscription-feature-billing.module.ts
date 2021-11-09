import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SubscriptionFeatureBillingComponent } from './list/subscription-feature-billing.component';
import { SubscriptionFeatureBillingDetailComponent } from './detail/subscription-feature-billing-detail.component';
import { SubscriptionFeatureBillingUpdateComponent } from './update/subscription-feature-billing-update.component';
import { SubscriptionFeatureBillingDeleteDialogComponent } from './delete/subscription-feature-billing-delete-dialog.component';
import { SubscriptionFeatureBillingRoutingModule } from './route/subscription-feature-billing-routing.module';

@NgModule({
  imports: [SharedModule, SubscriptionFeatureBillingRoutingModule],
  declarations: [
    SubscriptionFeatureBillingComponent,
    SubscriptionFeatureBillingDetailComponent,
    SubscriptionFeatureBillingUpdateComponent,
    SubscriptionFeatureBillingDeleteDialogComponent,
  ],
  entryComponents: [SubscriptionFeatureBillingDeleteDialogComponent],
})
export class SubscriptionFeatureBillingModule {}
