#!/bin/bash
echo "Switching to branch master .."
git checkout master
echo "Generating app .."
gulp build
if [[ ${PIPESTATUS[0]} -eq 0 ]]; then
  echo "Copying from dist/app/ to temporary directory .."
  mkdir ./.tmp/tmp-gh-pages
  cp -R dist/app ./.tmp/tmp-gh-pages/app
  echo "Switching to branch gh-pages .."
  git checkout gh-pages
  if [[ ${PIPESTATUS[0]} -eq 0 ]]; then
    echo "Copying from temporary directory to ./ .."
    cp -R ./.tmp/tmp-gh-pages/app/* ./
    read -p "Enter a commit message: " commitMessage
    git status
    git add -A .
    git commit -am "sync gh-pages" -m "$commitMessage"
    echo "Pushing to remote branch gh-pages .."
    git push origin gh-pages
    echo "Removing directory temporary directory .."
    rm -rf ./.tmp/tmp-gh-pages
    echo "Switching back to branch master .."
    git checkout master
  fi
fi
