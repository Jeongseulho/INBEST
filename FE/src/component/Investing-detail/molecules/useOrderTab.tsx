import { useState } from "react";

export const useOrderTab = (expectedPrice: number) => {
  const [amount, setAmount] = useState<number>(1);
  const [price, setPrice] = useState<number>(expectedPrice);
  const onChangeAmount = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (Number(e.target.value) < 1) {
      return;
    }
    setAmount(Number(e.target.value));
  };
  const onChangePrice = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPrice(Number(e.target.value));
  };

  return {
    amount,
    price,
    onChangeAmount,
    onChangePrice,
  };
};
