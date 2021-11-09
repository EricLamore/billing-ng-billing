import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IProductBilling, ProductBilling } from '../product-billing.model';
import { ProductBillingService } from '../service/product-billing.service';
import { TypeProduct } from 'app/entities/enumerations/type-product.model';

@Component({
  selector: 'jhi-product-billing-update',
  templateUrl: './product-billing-update.component.html',
})
export class ProductBillingUpdateComponent implements OnInit {
  isSaving = false;
  typeProductValues = Object.keys(TypeProduct);

  editForm = this.fb.group({
    id: [],
    name: [],
    lastUpdate: [],
    description: [],
    certificateTypes: [],
    commercialName: [],
    productType: [],
    settings: [],
    matrix: [],
  });

  constructor(protected productService: ProductBillingService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      this.updateForm(product);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductBilling>>): void {
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

  protected updateForm(product: IProductBilling): void {
    this.editForm.patchValue({
      id: product.id,
      name: product.name,
      lastUpdate: product.lastUpdate,
      description: product.description,
      certificateTypes: product.certificateTypes,
      commercialName: product.commercialName,
      productType: product.productType,
      settings: product.settings,
      matrix: product.matrix,
    });
  }

  protected createFromForm(): IProductBilling {
    return {
      ...new ProductBilling(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      lastUpdate: this.editForm.get(['lastUpdate'])!.value,
      description: this.editForm.get(['description'])!.value,
      certificateTypes: this.editForm.get(['certificateTypes'])!.value,
      commercialName: this.editForm.get(['commercialName'])!.value,
      productType: this.editForm.get(['productType'])!.value,
      settings: this.editForm.get(['settings'])!.value,
      matrix: this.editForm.get(['matrix'])!.value,
    };
  }
}
