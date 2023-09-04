module.exports = {
  root: true,
  env: { browser: true, es2020: true, node: true },
  extends: [
    "eslint:recommended",
    "plugin:@typescript-eslint/recommended",
    "plugin:react-hooks/recommended",
    "plugin:testing-library/react",
    "plugin:vitest/recommended",
  ],
  ignorePatterns: ["dist", ".eslintrc.cjs"],
  parser: "@typescript-eslint/parser",
  plugins: ["react-refresh", "testing-library", "vitest"],
  rules: {
    "react-refresh/only-export-components": ["warn", { allowConstantExport: true }],
    "max-params": ["error", 4], // 메소드 파라미터는 최대 4개 이하
    "max-lines-per-function": ["error", 50], // 메소드는 최대 50줄 이하
    "max-depth": ["error", 3], // 들여쓰기 깊이는 최대 3칸
    "quotes": ["error", "double"], // 문자열을 처리할 때는 쌍따옴표를 사용하도록 합니다.
    "semi": ["error", "always"], // 문장이 종료될 때는 세미콜론을 붙여줍니다.
    "arrow-parens": ["error", "always"], // 기본적으로 모든 함수를 화살표 함수 사용
    "@typescript-eslint/naming-convention": [
      "error",
      {
        selector: "typeLike",
        format: ["PascalCase"],
      },
    ],
  },
};
