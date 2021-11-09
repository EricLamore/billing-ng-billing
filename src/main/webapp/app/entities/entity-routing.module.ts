import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'customer-billing',
        data: { pageTitle: 'billingNgMongoApp.customer.home.title' },
        loadChildren: () => import('./customer-billing/customer-billing.module').then(m => m.CustomerBillingModule),
      },
      {
        path: 'settings-invoice-billing',
        data: { pageTitle: 'billingNgMongoApp.settingsInvoice.home.title' },
        loadChildren: () => import('./settings-invoice-billing/settings-invoice-billing.module').then(m => m.SettingsInvoiceBillingModule),
      },
      {
        path: 'settings-invoice-unit-billing',
        data: { pageTitle: 'billingNgMongoApp.settingsInvoiceUnit.home.title' },
        loadChildren: () =>
          import('./settings-invoice-unit-billing/settings-invoice-unit-billing.module').then(m => m.SettingsInvoiceUnitBillingModule),
      },
      {
        path: 'person-referer-billing',
        data: { pageTitle: 'billingNgMongoApp.personReferer.home.title' },
        loadChildren: () => import('./person-referer-billing/person-referer-billing.module').then(m => m.PersonRefererBillingModule),
      },
      {
        path: 'payment-method-billing',
        data: { pageTitle: 'billingNgMongoApp.paymentMethod.home.title' },
        loadChildren: () => import('./payment-method-billing/payment-method-billing.module').then(m => m.PaymentMethodBillingModule),
      },
      {
        path: 'address-billing',
        data: { pageTitle: 'billingNgMongoApp.address.home.title' },
        loadChildren: () => import('./address-billing/address-billing.module').then(m => m.AddressBillingModule),
      },
      {
        path: 'invoice-item-billing',
        data: { pageTitle: 'billingNgMongoApp.invoiceItem.home.title' },
        loadChildren: () => import('./invoice-item-billing/invoice-item-billing.module').then(m => m.InvoiceItemBillingModule),
      },
      {
        path: 'organization-billing',
        data: { pageTitle: 'billingNgMongoApp.organization.home.title' },
        loadChildren: () => import('./organization-billing/organization-billing.module').then(m => m.OrganizationBillingModule),
      },
      {
        path: 'rate-plan-billing',
        data: { pageTitle: 'billingNgMongoApp.ratePlan.home.title' },
        loadChildren: () => import('./rate-plan-billing/rate-plan-billing.module').then(m => m.RatePlanBillingModule),
      },
      {
        path: 'product-rate-plan-billing',
        data: { pageTitle: 'billingNgMongoApp.productRatePlan.home.title' },
        loadChildren: () =>
          import('./product-rate-plan-billing/product-rate-plan-billing.module').then(m => m.ProductRatePlanBillingModule),
      },
      {
        path: 'rate-plan-charge-billing',
        data: { pageTitle: 'billingNgMongoApp.ratePlanCharge.home.title' },
        loadChildren: () => import('./rate-plan-charge-billing/rate-plan-charge-billing.module').then(m => m.RatePlanChargeBillingModule),
      },
      {
        path: 'product-rate-plan-charge-billing',
        data: { pageTitle: 'billingNgMongoApp.productRatePlanCharge.home.title' },
        loadChildren: () =>
          import('./product-rate-plan-charge-billing/product-rate-plan-charge-billing.module').then(
            m => m.ProductRatePlanChargeBillingModule
          ),
      },
      {
        path: 'feature-billing',
        data: { pageTitle: 'billingNgMongoApp.feature.home.title' },
        loadChildren: () => import('./feature-billing/feature-billing.module').then(m => m.FeatureBillingModule),
      },
      {
        path: 'subscription-feature-billing',
        data: { pageTitle: 'billingNgMongoApp.subscriptionFeature.home.title' },
        loadChildren: () =>
          import('./subscription-feature-billing/subscription-feature-billing.module').then(m => m.SubscriptionFeatureBillingModule),
      },
      {
        path: 'product-billing',
        data: { pageTitle: 'billingNgMongoApp.product.home.title' },
        loadChildren: () => import('./product-billing/product-billing.module').then(m => m.ProductBillingModule),
      },
      {
        path: 'product-setting-billing',
        data: { pageTitle: 'billingNgMongoApp.productSetting.home.title' },
        loadChildren: () => import('./product-setting-billing/product-setting-billing.module').then(m => m.ProductSettingBillingModule),
      },
      {
        path: 'refund-billing',
        data: { pageTitle: 'billingNgMongoApp.refund.home.title' },
        loadChildren: () => import('./refund-billing/refund-billing.module').then(m => m.RefundBillingModule),
      },
      {
        path: 'consumption-billing',
        data: { pageTitle: 'billingNgMongoApp.consumption.home.title' },
        loadChildren: () => import('./consumption-billing/consumption-billing.module').then(m => m.ConsumptionBillingModule),
      },
      {
        path: 'invoice-billing',
        data: { pageTitle: 'billingNgMongoApp.invoice.home.title' },
        loadChildren: () => import('./invoice-billing/invoice-billing.module').then(m => m.InvoiceBillingModule),
      },
      {
        path: 'subscription-tmp-billing',
        data: { pageTitle: 'billingNgMongoApp.subscriptionTmp.home.title' },
        loadChildren: () => import('./subscription-tmp-billing/subscription-tmp-billing.module').then(m => m.SubscriptionTmpBillingModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
