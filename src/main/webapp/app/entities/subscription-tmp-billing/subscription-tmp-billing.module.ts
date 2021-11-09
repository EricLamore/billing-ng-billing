import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SubscriptionTmpBillingComponent } from './list/subscription-tmp-billing.component';
import { SubscriptionTmpBillingDetailComponent } from './detail/subscription-tmp-billing-detail.component';
import { SubscriptionTmpBillingUpdateComponent } from './update/subscription-tmp-billing-update.component';
import { SubscriptionTmpBillingDeleteDialogComponent } from './delete/subscription-tmp-billing-delete-dialog.component';
import { SubscriptionTmpBillingRoutingModule } from './route/subscription-tmp-billing-routing.module';

@NgModule({
  imports: [SharedModule, SubscriptionTmpBillingRoutingModule],
  declarations: [
    SubscriptionTmpBillingComponent,
    SubscriptionTmpBillingDetailComponent,
    SubscriptionTmpBillingUpdateComponent,
    SubscriptionTmpBillingDeleteDialogComponent,
  ],
  entryComponents: [SubscriptionTmpBillingDeleteDialogComponent],
})
export class SubscriptionTmpBillingModule {}
