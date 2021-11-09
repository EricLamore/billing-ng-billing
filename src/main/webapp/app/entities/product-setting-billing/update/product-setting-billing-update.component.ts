import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IProductSettingBilling, ProductSettingBilling } from '../product-setting-billing.model';
import { ProductSettingBillingService } from '../service/product-setting-billing.service';

@Component({
  selector: 'jhi-product-setting-billing-update',
  templateUrl: './product-setting-billing-update.component.html',
})
export class ProductSettingBillingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    value: [],
  });

  constructor(
    protected productSettingService: ProductSettingBillingService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productSetting }) => {
      this.updateForm(productSetting);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productSetting = this.createFromForm();
    if (productSetting.id !== undefined) {
      this.subscribeToSaveResponse(this.productSettingService.update(productSetting));
    } else {
      this.subscribeToSaveResponse(this.productSettingService.create(productSetting));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductSettingBilling>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(productSetting: IProductSettingBilling): void {
    this.editForm.patchValue({
      id: productSetting.id,
      name: productSetting.name,
      value: productSetting.value,
    });
  }

  protected createFromForm(): IProductSettingBilling {
    return {
      ...new ProductSettingBilling(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      value: this.editForm.get(['value'])!.value,
    };
  }
}
