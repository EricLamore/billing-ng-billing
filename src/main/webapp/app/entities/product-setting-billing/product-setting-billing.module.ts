import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProductSettingBillingComponent } from './list/product-setting-billing.component';
import { ProductSettingBillingDetailComponent } from './detail/product-setting-billing-detail.component';
import { ProductSettingBillingUpdateComponent } from './update/product-setting-billing-update.component';
import { ProductSettingBillingDeleteDialogComponent } from './delete/product-setting-billing-delete-dialog.component';
import { ProductSettingBillingRoutingModule } from './route/product-setting-billing-routing.module';

@NgModule({
  imports: [SharedModule, ProductSettingBillingRoutingModule],
  declarations: [
    ProductSettingBillingComponent,
    ProductSettingBillingDetailComponent,
    ProductSettingBillingUpdateComponent,
    ProductSettingBillingDeleteDialogComponent,
  ],
  entryComponents: [ProductSettingBillingDeleteDialogComponent],
})
export class ProductSettingBillingModule {}
