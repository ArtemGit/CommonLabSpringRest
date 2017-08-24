<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .own-length
    {
        width: 250px;
    }
</style>
<div class="panel-heading"><span class="lead">Форма регистрации стоимости реализации имущества  <span >${assetName}</span></span></div>
<div class="formcontainer">
    <form  ng-submit="changeApplicationStatusRelease(price)" name="myForm" class="form-horizontal">

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-2 control-lable" for="price">Укажите Цену по которой реализовали имущество</label>
                <div class="col-md-7">
                    <input type="number" ng-model="price" name="price" id="price" class="username form-control input-sm" placeholder="Старая Цена выставления на продажу ${startCost}" positive  required/>
                    <div class="has-error" ng-show="myForm.price.$dirty">
                        <span ng-show="myForm.price.$error.required">Это поле обязательно для заполнения</span>
                        <span ng-show="myForm.price.$invalid">Это поле заполненнр некорректно</span>
                        <span ng-show="myForm.price.$error.positive">Поле не может принимать отрицаткльные значения</span>
                    </div>
                </div>

            </div>
        </div>

        <div class="row">
            <div class="floatRight">
               <input  type="submit" class="btn btn-success own-length" value="Отметить как реализованное"   ng-disabled="myForm.$invalid"/>
               <input  ng-click="closeForm()" class="btn btn-info " value="Скрыть форму"  />
            </div>
        </div>
    </form>
</div>
